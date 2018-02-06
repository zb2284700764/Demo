package com.demo.design_pattern.factory.simple.product;

/**
 * A 产品
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午9:59:43
 */
public class ProductA implements IProduct {

	@Override
	public void production() {
		System.out.println("生产A产品");
	}

}
