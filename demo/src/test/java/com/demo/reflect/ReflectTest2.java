package com.demo.reflect;

/**
 * 反射测试类
 * @author zhoubin
 *
 * @createDate 2017年9月8日 下午3:22:00
 */
public class ReflectTest2 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {


		String className = "demo.reflect.ExtendType";
		
		// 下面两种加载方式都可以加载类
		Class<?> c = Class.forName(className);
//		Class<?> c = ExtendType.class;
		
//		ClassLoader.getSystemClassLoader().loadClass(className);
		
		ExtendType et = (ExtendType) c.newInstance();
		et.sayHello("World");
		
		System.out.println(et.getClass().getClassLoader().getClass().getName());
		
	}

}
