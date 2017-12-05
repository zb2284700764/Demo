package com.demo.proxy.DynamicProxy.cglib.demo;

/**
 * 需要被代理的类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月25日 下午4:30:40
 */
public class Book {
	public void addBook() {
		System.out.println("添加图书");
	}
}
