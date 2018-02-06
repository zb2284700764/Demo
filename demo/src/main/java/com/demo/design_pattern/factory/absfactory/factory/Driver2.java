package com.demo.design_pattern.factory.absfactory.factory;

import com.demo.design_pattern.factory.absfactory.product.AudiCar;
import com.demo.design_pattern.factory.absfactory.product.BenzCar;
import com.demo.design_pattern.factory.absfactory.product.BmwCar;

/**
 * 简单工厂配合反射改进抽象工厂
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:50:37
 */
public class Driver2 {
	public static BenzCar createBenzCar(String car) throws Exception {
		return (BenzCar) Class.forName(car).newInstance();
	}

	public static BmwCar createBmwCar(String car) throws Exception {
		return (BmwCar) Class.forName(car).newInstance();
	}

	public static AudiCar createAudiCar(String car) throws Exception {
		return (AudiCar) Class.forName(car).newInstance();
	}
}
