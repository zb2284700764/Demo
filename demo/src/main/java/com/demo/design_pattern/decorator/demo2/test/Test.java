package com.demo.design_pattern.decorator.demo2.test;

import com.demo.design_pattern.decorator.demo2.component.Person;
import com.demo.design_pattern.decorator.demo2.concreteComponent.Man;
import com.demo.design_pattern.decorator.demo2.concreteDecorator.Decorator_one;
import com.demo.design_pattern.decorator.demo2.concreteDecorator.Decorator_three;
import com.demo.design_pattern.decorator.demo2.concreteDecorator.Decorator_two;
import com.demo.design_pattern.decorator.demo2.decorator.Decorator;

public class Test {
	public static void main(String[] args) {
		Person man = new Man();
		Decorator decorator = new Decorator_three(new Decorator_two(new Decorator_one(man)));
		decorator.wearClothes();
		decorator.walkToWhere();
	}
}
