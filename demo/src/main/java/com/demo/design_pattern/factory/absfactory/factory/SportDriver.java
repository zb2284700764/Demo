package com.demo.design_pattern.factory.absfactory.factory;

import com.demo.design_pattern.factory.absfactory.product.AudiCar;
import com.demo.design_pattern.factory.absfactory.product.AudiSportCar;
import com.demo.design_pattern.factory.absfactory.product.BenzCar;
import com.demo.design_pattern.factory.absfactory.product.BenzSportCar;
import com.demo.design_pattern.factory.absfactory.product.BmwCar;
import com.demo.design_pattern.factory.absfactory.product.BmwSportCar;

/**
 * 生产跑车(产品族)的工厂，生产各种品牌的跑车
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:51:40
 */
public class SportDriver extends Driver {

	@Override
	public BenzCar createBenzCar(String car) throws Exception {
		return new BenzSportCar();
	}

	@Override
	public BmwCar createBmwCar(String car) throws Exception {
		return new BmwSportCar();
	}

	@Override
	public AudiCar createAudiCar(String car) throws Exception {
		return new AudiSportCar();
	}

}
