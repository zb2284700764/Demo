package com.demo.proxy.DynamicProxy.cglib;

import com.demo.proxy.DynamicProxy.cglib.demo.Book;
import com.demo.proxy.DynamicProxy.cglib.demo.CglibDynamicProxy;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年9月25日 下午5:10:45
 */
public class TestCglib {

	public static void main(String[] args) {
		CglibDynamicProxy cglib = new CglibDynamicProxy();
		Book book = (Book) cglib.getProxy(new Book());
		book.addBook();
	}

}
