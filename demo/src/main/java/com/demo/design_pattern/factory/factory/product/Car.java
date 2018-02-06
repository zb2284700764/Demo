package com.demo.design_pattern.factory.factory.product;

/**
 * 抽象产品
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:25:40
 */
public abstract class Car {
	private String name;

	public abstract void drive();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
