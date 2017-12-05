package com.demo.proxy.DynamicProxy.jdk.demo2;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年9月13日 下午2:41:25
 */
public class UserServiceImpl implements UserService {

	@Override
	public void add() {
		System.out.println("----- add -----");
	}

}
