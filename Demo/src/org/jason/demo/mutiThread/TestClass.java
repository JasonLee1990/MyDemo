package org.jason.demo.mutiThread;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TestClass {

	ScheduledExecutorService sExec;

	Runnable beeper;

	ScheduledFuture<?> beeperHanle;

	ScheduledFuture<?> beeperHanle2;

	public void testThreadPool() {
		ExecutorService exec = Executors.newFixedThreadPool(2);
		for (int index = 0; index < 100; index++) {
			Runnable run = new Runnable() {

				@Override
				public void run() {
					long time = (long) (Math.random() * 1000);
					System.out.println("sleeping " + time + " ms");
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}

	@Before
	public void initParam() {
		sExec = Executors.newScheduledThreadPool(2);
		beeper = new Runnable() {
			int count = 0;

			@Override
			public void run() {
				System.out.println(new Date() + " 叫一声 " + (++count));
			}
		};
		// 1秒钟后运行，并每隔2秒运行一次
		beeperHanle = sExec.scheduleAtFixedRate(beeper, 1, 2, TimeUnit.SECONDS);
		// 2秒钟后运行，并每次在上次任务运行完后等待5秒后重新运行
		beeperHanle2 = sExec.scheduleWithFixedDelay(beeper, 2, 5, TimeUnit.SECONDS);
	}

	@Test
	public void testBeeper() {
		sExec.schedule(new Runnable() {

			@Override
			public void run() {
				beeperHanle.cancel(true);
				beeperHanle2.cancel(true);
				sExec.shutdown();
			}

		}, 1000, TimeUnit.SECONDS);
	}
	
	@Test
	public void testsss() {
		String str1 = "".replaceAll("[^\\d]", "");
		System.out.println("result : "+ str1);
	}
}
