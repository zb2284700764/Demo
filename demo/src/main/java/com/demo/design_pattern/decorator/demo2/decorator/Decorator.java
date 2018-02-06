package com.demo.design_pattern.decorator.demo2.decorator;

import com.demo.design_pattern.decorator.demo2.component.Person;

/**
 * 抽象装饰者
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 上午10:55:24
 */
public abstract class Decorator implements Person {

	Person person;

	public Decorator(Person person) {
		this.person = person;
	}

	@Override
	public void wearClothes() {
		person.wearClothes();
	}

	@Override
	public void walkToWhere() {
		person.walkToWhere();
	}

}
