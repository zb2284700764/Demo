package com.demo.design_pattern.factory.absfactory.product;

/**
 * 具体产品，Audi 跑车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:47:29
 */
public class AudiSportCar extends AudiCar {

	@Override
	public void drive() {
		System.out.println(this.getName() + "----AudiSportCar-----------------------");
	}

}
