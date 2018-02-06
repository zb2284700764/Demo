package com.demo.design_pattern.factory.absfactory.factory;

import com.demo.design_pattern.factory.absfactory.product.AudiCar;
import com.demo.design_pattern.factory.absfactory.product.BenzCar;
import com.demo.design_pattern.factory.absfactory.product.BmwCar;

/**
 * 抽象工厂
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:50:37
 */
public abstract class Driver {
	public abstract BenzCar createBenzCar(String car) throws Exception;

	public abstract BmwCar createBmwCar(String car) throws Exception;

	public abstract AudiCar createAudiCar(String car) throws Exception;
}
