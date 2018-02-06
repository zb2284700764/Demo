package com.demo.design_pattern.decorator.demo3.concreteDecorator;

import com.demo.design_pattern.decorator.demo3.component.Humburger;
import com.demo.design_pattern.decorator.demo3.decorator.Condiment;

/**
 * 具体的装饰者类 生菜
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午1:56:46
 */
public class Lettuce extends Condiment {
	
	Humburger humburger;
	
	public Lettuce(Humburger humburger) {
		this.humburger = humburger;
	}

	@Override
	public String getName() {
		return this.humburger.getName() + " 加生菜";
	}

	@Override
	public double getPrice() {
		return this.humburger.getPrice() + 2;
	}

}
