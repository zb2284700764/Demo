package com.demo.design_pattern.decorator.demo3.concreteComponent;

import com.demo.design_pattern.decorator.demo3.component.Humburger;

/**
 * 被装饰的类 肌肉汉堡包
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午1:52:26
 */
public class ChickenBurger extends Humburger {

	public ChickenBurger() {
		name = "鸡腿堡";
	}

	@Override
	public double getPrice() {
		return 10;
	}

}
