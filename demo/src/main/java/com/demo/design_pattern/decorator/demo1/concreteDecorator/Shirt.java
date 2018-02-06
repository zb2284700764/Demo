package com.demo.design_pattern.decorator.demo1.concreteDecorator;

import com.demo.design_pattern.decorator.demo1.component.Person;
import com.demo.design_pattern.decorator.demo1.decorator.ClothingDecorator;

/**
 * 上面既然已经创建了两种Decorator，那么我们基于它们进行拓展，创建出不同的装饰者，对于Clothing，我们新建Shirt.java
 * 具体装饰-衬衫
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:35:53
 */
public class Shirt extends ClothingDecorator {

	// 用实例变量保存Person的引用
	Person person;

	public Shirt(Person person) {
		this.person = person;
	}

	@Override
	public String getDescription() {
		return person.getDescription() + " 一个衬衫 ";
	}

	@Override
	public double cost() {
		return 100 + person.cost(); // 实现了cost()方法，并调用了person的cost()方法，目的是获得所有累加值
	}

}
