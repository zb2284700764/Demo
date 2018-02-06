package com.demo.design_pattern.factory.absfactory.product;

/**
 * 具体产品，Bmw 跑车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:47:29
 */
public class BmwSportCar extends BmwCar {

	@Override
	public void drive() {
		System.out.println(this.getName() + "----BmwSportCar-----------------------");
	}

}
