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
public class Decorator_three extends Decorator {

	public Decorator_three(Person person) {
		super(person);
	}

	public void findClothes() {
		System.out.println("找到一件D&G。。");
	}

	public void findTheTarget() {
		System.out.println("在Map上找到神秘花园和城堡。。");
	}

	@Override
	public void wearClothes() {
		super.wearClothes();
		findClothes();
	}

	@Override
	public void walkToWhere() {
		super.walkToWhere();
		findTheTarget();
	}
}
