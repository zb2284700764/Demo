package com.demo.proxy.DynamicProxy.cglib.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 实现的动态代理类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月25日 下午4:19:24
 */
public class CglibDynamicProxy implements MethodInterceptor {
	private Object target;

	public Object getProxy(Object target) {
		this.target = target;
		
		// 增强器，动态代码生成器
		Enhancer enhancer = new Enhancer();

		// 设置需要创建子类的类
		enhancer.setSuperclass(this.target.getClass());
		// 设置回调
		enhancer.setCallback(this);
		return enhancer.create();
	}

	// 实现MethodInterceptor接口方法
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("前置代理");
		// 通过代理类调用父类中的方法
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("后置代理");
		return result;
	}

}
