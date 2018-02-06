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
public class Decorator_two extends Decorator {

	public Decorator_two(Person person) {
		super(person);
	}

	public void goClothespress() {
		System.out.println("去衣柜找找看。。");
	}

	public void findPlaceOnMap() {
		System.out.println("在Map上找找。。");
	}

	@Override
	public void wearClothes() {
		super.wearClothes();
		goClothespress();
	}

	@Override
	public void walkToWhere() {
		super.walkToWhere();
		findPlaceOnMap();
	}
}
