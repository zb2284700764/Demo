package com.demo.proxy.DynamicProxy.jdk.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理处理类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月13日 下午2:42:10
 */
public class MyInvocationHandler implements InvocationHandler {

	// 真实的对象
	private Object target;
	
	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}
	
	public Object getProxy() {
		System.out.println(this.getClass().getClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader());

		/* 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数 
		 * 第一个参数 Thread.currentThread().getContextClassLoader()，我们这里使用当前 handler 这个类的 ClassLoader 对象来加载我们的代理对象 
		 * 第二个参数 this.target.getClass().getInterfaces()，为代理对象提供接口的是真实对象所实现的接口，表示代理的是该真实对象，这样就能调用这组接口中的方法了 
		 * 第三个参数 this 当前类对象，将代理对象和当前 handler 关联在一块 
		 */
//		return Proxy.newProxyInstance(MyInvocationHandler.class.getContextClassLoader(), this.target.getClass().getInterfaces(), this);
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), this.target.getClass().getInterfaces(), this);
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("-------- before --------");
		System.out.println("Method:" + method);
		// 当代理对象调用真实对象的方法的时候，其会自动跳转到代理对象关联的 handler 的 invoke 方法来进行调用
		Object result = method.invoke(this.target, args);
		System.out.println("-------- after --------");
		
		return result;
	}

}
