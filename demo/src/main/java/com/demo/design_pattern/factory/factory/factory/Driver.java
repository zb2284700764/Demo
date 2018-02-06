package com.demo.design_pattern.factory.factory.factory;

import com.demo.design_pattern.factory.factory.product.Car;

/**
 * 抽象工厂
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:29:02
 */
public abstract class Driver {
	/**
	 * 创建车
	 * 
	 * @param car
	 * @return
	 * @throws Exception
	 * @author zhoubin
	 * @createDate 2017年7月13日 下午10:29:29
	 */
	public abstract Car createCar(String car) throws Exception;
}
