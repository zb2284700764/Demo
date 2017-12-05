package com.demo.proxy.StaticProxy;

/**
 * 代理类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月25日 上午11:15:58
 */
public class CountProxy implements Count {

	private Count count;

	public CountProxy(Count count) {
		this.count = count;
	}

	@Override
	public void queryCount() {
		System.out.println("代理类：在调用真实对象方法之前做的一些操作");
		this.count.queryCount();
		System.out.println("代理类：在调用真实对象方法之后做的一些操作");
	}

	@Override
	public void updateCount() {
		System.out.println("代理类：在调用真实对象方法之前做的一些操作");
		this.count.updateCount();
		System.out.println("代理类：在调用真实对象方法之后做的一些操作");
	}

}
