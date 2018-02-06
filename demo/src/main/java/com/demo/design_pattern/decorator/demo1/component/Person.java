package com.demo.design_pattern.decorator.demo1.component;

/**
 * 因为总体对象是人，所以把人抽象为基类
 * 抽象组件
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:13:52
 */
public abstract class Person {
	protected String description = "Unkonwn";

	public String getDescription() {
		return description;
	}

	public abstract double cost(); // 子类应该实现的方法



}