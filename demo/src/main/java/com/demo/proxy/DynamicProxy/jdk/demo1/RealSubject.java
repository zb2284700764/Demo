package com.demo.proxy.DynamicProxy.jdk.demo1;

/**
 * 真实的对象
 * @author zhoubin
 *
 * @createDate 2017年9月13日 上午11:08:09
 */
public class RealSubject implements Subject {

	@Override
	public void rent() {
		System.out.println("rent");
	}

	@Override
	public void hello(String str) {
		System.out.println("hello " + str);
	}

}
