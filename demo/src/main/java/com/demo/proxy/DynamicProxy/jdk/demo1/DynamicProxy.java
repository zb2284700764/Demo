package com.demo.proxy.DynamicProxy.jdk.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类，每个代理类都必须实现 InvocationHandler 接口
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月13日 上午11:09:54
 */
public class DynamicProxy implements InvocationHandler {

	private Object subject; // 要代理的真实对象

	/**
	 * 通过构造方法为要代理的真实对象赋值
	 * 
	 * @param subject
	 */
	public DynamicProxy(Object subject) {
		this.subject = subject;
	}

	/**
	 * proxy:  指代我们所代理的那个真实对象
	 * method:  指代的是我们所要调用真实对象的某个方法的Method对象
	 * args:  指代的是调用真实对象某个方法时接受的参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("代理之前的一些操作");
		System.out.println("Method：" + method);
		
		// 当代理对象调用真实对象的方法的时候，其会自动跳转到代理对象关联的 handler 对象的 invoke 方法来进行调用
		method.invoke(this.subject, args);
		
		System.out.println("代理之后的一些操作");

		return null;
	}

}
