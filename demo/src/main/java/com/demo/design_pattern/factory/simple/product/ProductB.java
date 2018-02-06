package com.demo.design_pattern.factory.simple.product;

/**
 * B 产品
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:00:04
 */
public class ProductB implements IProduct {

	@Override
	public void production() {
		System.out.println("生产B产品");
	}

}
