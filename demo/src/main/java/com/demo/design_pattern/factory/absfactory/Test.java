package com.demo.design_pattern.factory.absfactory;

import com.demo.design_pattern.factory.absfactory.factory.BusinessDriver;
import com.demo.design_pattern.factory.absfactory.factory.Driver;
import com.demo.design_pattern.factory.absfactory.product.AudiCar;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:53:59
 */
public class Test {

	public static void main(String[] args) throws Exception {
		Driver d1 = new BusinessDriver();
		AudiCar audiCar = d1.createAudiCar("audi");
		audiCar.setName("audi");
		audiCar.drive();
	}
}
