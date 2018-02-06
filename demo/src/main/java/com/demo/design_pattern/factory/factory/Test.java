package com.demo.design_pattern.factory.factory;

import com.demo.design_pattern.factory.factory.factory.BmwDriver;
import com.demo.design_pattern.factory.factory.factory.Driver;
import com.demo.design_pattern.factory.factory.product.Car;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:32:04
 */
public class Test {
	public static void main(String[] args) throws Exception {
		Driver driver = new BmwDriver();
		Car car = driver.createCar("BMW");
		car.setName("bmw");
		car.drive();
	}
}
