package com.demo.design_pattern.decorator.demo1.test;

import com.demo.design_pattern.decorator.demo1.component.Person;
import com.demo.design_pattern.decorator.demo1.concreteComponent.Teenager;
import com.demo.design_pattern.decorator.demo1.concreteDecorator.Casquette;
import com.demo.design_pattern.decorator.demo1.concreteDecorator.Shirt;

/**
 * 测试类
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:38:40
 */
public class Test {
	public static void main(String[] args) {
		Person person = new Teenager();

		person = new Shirt(person);
		person = new Casquette(person);

		System.out.println(person.getDescription() + " ￥ " + person.cost());
	}
}
