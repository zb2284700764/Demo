package com.demo.design_pattern.decorator.demo1.concreteDecorator;

import com.demo.design_pattern.decorator.demo1.component.Person;
import com.demo.design_pattern.decorator.demo1.decorator.ClothingDecorator;

/**
 * 上面既然已经创建了两种Decorator，那么我们基于它们进行拓展，创建出不同的装饰者，对于Hat，我们新建 Casquette.java
 * 具体装饰-鸭舌帽
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:34:54
 */
public class Casquette extends ClothingDecorator {

	Person person;

	public Casquette(Person person) {
		this.person = person;
	}

	@Override
	public String getDescription() {
		return person.getDescription() + " 一个鸭舌帽 ";
	}

	@Override
	public double cost() {
		return 75 + person.cost();
	}

}
