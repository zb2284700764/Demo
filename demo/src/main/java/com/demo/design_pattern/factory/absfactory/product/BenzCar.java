package com.demo.design_pattern.factory.absfactory.product;

/**
 * 抽象产品，Benz 汽车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:46:12
 */
public abstract class BenzCar {

	private String name;

	public abstract void drive();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
