package com.demo.proxy.StaticProxy;

/**
 * Count 实现类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月25日 上午11:09:34
 */
public class CountImpl implements Count {

	@Override
	public void queryCount() {
		System.out.println("查看账户");
	}

	@Override
	public void updateCount() {
		System.out.println("修改账户");
	}

}
