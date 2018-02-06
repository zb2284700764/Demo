package com.demo.design_pattern.factory.absfactory.product;

/**
 * 具体产品，Bmw 商务车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:48:19
 */
public class BmwBusinessCar extends BmwCar {

	@Override
	public void drive() {
		System.out.println(this.getName() + "----BmwBusinessCar-----------------------");
	}

}
