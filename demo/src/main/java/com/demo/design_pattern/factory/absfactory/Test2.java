package com.demo.design_pattern.factory.absfactory;

import com.demo.design_pattern.factory.absfactory.factory.Driver2;
import com.demo.design_pattern.factory.absfactory.product.AudiCar;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:53:59
 */
public class Test2 {

	public static void main(String[] args) throws Exception {

		AudiCar car = Driver2.createAudiCar("com.factory.absfactory.product.AudiSportCar");
		car.setName("audi");
		car.drive();
	}
}
