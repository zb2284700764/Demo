package com.demo.proxy.DynamicProxy.jdk.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年9月13日 上午11:22:52
 */
public class Test {
	
	
	public static void main(String[] args) {
		
		// 要代理的真实对象
		Subject realSubject = new RealSubject();
		
		// 要代理哪个真实对象，就将哪个真实对象传进去，代理对象是通过真实对象来调用其方法的
		InvocationHandler handler = new DynamicProxy(realSubject);
		
		/** 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数 
		 * 第一个参数 handler.getClass().getClassLoader() ，指定当前目标对象使用类加载器,获取加载器的方法是固定的
		 * 第二个参数realSubject.getClass().getInterfaces()，目标对象实现的接口的类型,使用泛型方式确认类型，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了 
		 * 第三个参数handler，事件处理,执行目标对象的方法时,会触发事件处理器的方法,会把当前执行目标对象的方法作为参数传入
		 */
		Subject proxySubject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);
		
		System.out.println(proxySubject.getClass().getName());
		proxySubject.rent();
		proxySubject.hello("world");
	}
	
	
}

