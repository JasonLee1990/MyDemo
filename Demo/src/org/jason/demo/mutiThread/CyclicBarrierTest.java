package org.jason.demo.mutiThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

public class CyclicBarrierTest {
	// 徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
	int[] timeWalk = { 5, 8, 15, 15, 10 };
	// 自驾游
	int[] timeSelf = { 1, 3, 4, 4, 5 };
	// 旅游大巴
	int[] timeBus = { 2, 4, 6, 6, 7 };
	
	CyclicBarrier barrier;
	
	ExecutorService exec;

	static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date()) + ": ";
	}

	@Before
	public void initParam() {
	    barrier = new CyclicBarrier(3);
	    exec = Executors.newFixedThreadPool(3);
	}

	@Test
	public void testCyclicBarrier() {
	    // 三个旅行团
	    exec.submit(new Tour(barrier, "WalkTour", timeWalk));
	    exec.submit(new Tour(barrier, "SelfTour", timeSelf));
	    exec.submit(new Tour(barrier, "BusTour", timeBus));
	    exec.shutdown();
	}

	static class Tour implements Runnable {
		private int[] times;
		private CyclicBarrier barrier;
		private String tourName;

		public Tour(CyclicBarrier barrier, String tourName, int[] times) {
			this.times = times;
			this.tourName = tourName;
			this.barrier = barrier;
		}

		public void run() {
			try {
				Thread.sleep(times[0] * 1000);
				System.out.println(now() + tourName + " Reached Shenzhen");
				barrier.await();
				Thread.sleep(times[1] * 1000);
				System.out.println(now() + tourName + " Reached Guangzhou");
				barrier.await();
				Thread.sleep(times[2] * 1000);
				System.out.println(now() + tourName + " Reached Shaoguan");
				barrier.await();
				Thread.sleep(times[3] * 1000);
				System.out.println(now() + tourName + " Reached Changsha");
				barrier.await();
				Thread.sleep(times[4] * 1000);
				System.out.println(now() + tourName + " Reached Wuhan");
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
}
