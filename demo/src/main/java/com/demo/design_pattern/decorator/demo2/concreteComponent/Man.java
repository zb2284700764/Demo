package com.demo.design_pattern.decorator.demo2.concreteComponent;

import com.demo.design_pattern.decorator.demo2.component.Person;

/**
 * 被装饰者
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 上午10:54:21
 */
public class Man implements Person {

	@Override
	public void wearClothes() {
		System.out.println("穿什么呢。。");
	}

	@Override
	public void walkToWhere() {
		System.out.println("去哪里呢。。");
	}

}
