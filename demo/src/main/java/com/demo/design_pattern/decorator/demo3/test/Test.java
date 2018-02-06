package com.demo.design_pattern.decorator.demo3.test;

import com.demo.design_pattern.decorator.demo3.component.Humburger;
import com.demo.design_pattern.decorator.demo3.concreteComponent.ChickenBurger;
import com.demo.design_pattern.decorator.demo3.concreteDecorator.Chilli;
import com.demo.design_pattern.decorator.demo3.concreteDecorator.Lettuce;

/**
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午2:00:32
 */
public class Test {

	public static void main(String[] args) {
		// 创建一个基本的鸡腿堡
		Humburger chickenBurger = new ChickenBurger();
		System.out.println(chickenBurger.getName() + " 价格 " + chickenBurger.getPrice());

		// 加辣椒
		Chilli chilli = new Chilli(chickenBurger);
		System.out.println(chilli.getName() + " 价格 " + chilli.getPrice());
		
		// 加生菜
		Lettuce lettuce = new Lettuce(chickenBurger);
		System.out.println(lettuce.getName() + " 价格 " + lettuce.getPrice());
		
		// 加辣椒又加生菜
		Lettuce lettuceBurger = new Lettuce(chilli);
		System.out.println(lettuceBurger.getName() + " 价格 " + lettuceBurger.getPrice());
	}

}
