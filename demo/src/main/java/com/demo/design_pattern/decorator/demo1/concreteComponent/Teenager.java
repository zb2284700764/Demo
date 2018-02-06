package com.demo.design_pattern.decorator.demo1.concreteComponent;

import com.demo.design_pattern.decorator.demo1.component.Person;

/**
 * 客户分为很多种，有儿童、青少年、成年人等，因此我们可以创建不同的被装饰者，这里我们创建青少年的被装饰者
 * 具体组件
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:28:54
 */
public class Teenager extends Person {

	public Teenager() {
		description = "购物清单: ";
	}

	@Override
	public double cost() {
		// 花费为 0
		return 0;
	}

}
