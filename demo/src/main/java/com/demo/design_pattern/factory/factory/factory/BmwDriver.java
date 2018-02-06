package com.demo.design_pattern.factory.factory.factory;

import com.demo.design_pattern.factory.factory.product.Bmw;
import com.demo.design_pattern.factory.factory.product.Car;

/**
 * 具体工厂，每个工厂负责一个具体产品，只生产 BMW 车
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:30:41
 */
public class BmwDriver extends Driver {

	@Override
	public Car createCar(String car) throws Exception {
		return new Bmw();
	}

}
