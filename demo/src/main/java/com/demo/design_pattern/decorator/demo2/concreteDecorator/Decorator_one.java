package com.demo.design_pattern.decorator.demo2.concreteDecorator;

import com.demo.design_pattern.decorator.demo2.component.Person;
import com.demo.design_pattern.decorator.demo2.decorator.Decorator;

/**
 * 具体装饰A
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 上午10:56:59
 */
public class Decorator_one extends Decorator {

	public Decorator_one(Person person) {
		super(person);
	}

	public void goHome() {
		System.out.println("进房子。。");
	}

	public void findMap() {
		System.out.println("书房找找Map。。");
	}

	@Override
	public void wearClothes() {
		super.wearClothes();
		goHome();
	}

	@Override
	public void walkToWhere() {
		super.walkToWhere();
		findMap();
	}
}
