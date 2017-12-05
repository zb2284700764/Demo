package com.demo.proxy.StaticProxy;

public class Test {

	public static void main(String[] args) {
		
		Count count = new CountImpl();
		CountProxy countProxy = new CountProxy(count);
		
		count.queryCount();
		System.out.println();
		countProxy.queryCount();
		System.out.println();
		countProxy.updateCount();
		
	}
	
}

