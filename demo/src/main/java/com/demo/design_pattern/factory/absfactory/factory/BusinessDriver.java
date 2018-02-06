package com.demo.design_pattern.factory.absfactory.factory;

import com.demo.design_pattern.factory.absfactory.product.AudiBusinessCar;
import com.demo.design_pattern.factory.absfactory.product.AudiCar;
import com.demo.design_pattern.factory.absfactory.product.BenzBusinessCar;
import com.demo.design_pattern.factory.absfactory.product.BenzCar;
import com.demo.design_pattern.factory.absfactory.product.BmwBusinessCar;
import com.demo.design_pattern.factory.absfactory.product.BmwCar;

/**
 * 生产商务车(产品族)的工厂，生产各种品牌的商务车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:51:40
 */
public class BusinessDriver extends Driver {

	@Override
	public BenzCar createBenzCar(String car) throws Exception {
		return new BenzBusinessCar();
	}

	@Override
	public BmwCar createBmwCar(String car) throws Exception {
		return new BmwBusinessCar();
	}

	@Override
	public AudiCar createAudiCar(String car) throws Exception {
		return new AudiBusinessCar();
	}

}
