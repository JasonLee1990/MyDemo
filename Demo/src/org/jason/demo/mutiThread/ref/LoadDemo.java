package org.jason.demo.mutiThread.ref;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;

import org.jason.demo.entity.Operation;
import org.jason.demo.repository.OperationRepository;
import org.springframework.web.bind.annotation.RequestMapping;

public class LoadDemo {

	@Resource
	OperationRepository operationRepository;

	// 从一个包中查找 出所有的类，在jar包中不能查找
	@SuppressWarnings("rawtypes")
	private static List<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();// 获取当前的线程的类加载器
		String path = packageName.replace('.', '/');// 将包名替换成相对路经名
		Enumeration<URL> resources;

		resources = classLoader.getResources(path);// 查找所有给定名称的资源
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));// 获取此 URL 的文件名 创建文件对象
			// 添加到List里面
		}
		List<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;

	}

	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File direcotry, String packageName) throws ClassNotFoundException {

		List<Class> classes = new ArrayList<Class>();
		if (!direcotry.exists()) {
			return classes;
		}
		File[] files = direcotry.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));

			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));

			}

		}
		return classes;

	}

	@SuppressWarnings({ "rawtypes" })
	public List<Operation> loadAllController(String path) throws ClassNotFoundException, IOException {
		List<Class> clazzes = LoadDemo.getClasses(path);
		List<Operation> operations = new ArrayList<Operation>();
		// 获取到所有controller类
		for (Class<?> cls : clazzes) {
			Method[] ms = cls.getMethods();
			// 迭代类中所有方法
			for (Method m : ms) {
				Operation op = new Operation();
				// 获取注解
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				if (rm != null) {
					if ("".equals(rm.operation()) || rm.value().length == 0) {
						continue;
					}
					op.setName(rm.operation());
					op.setUrl(rm.value()[0]);
					op.setPermission(rm.permission());
					operations.add(op);
				}

			}
		}
		return operations;

	}

}
