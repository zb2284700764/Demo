package com.demo.design_pattern.decorator.demo3.component;

/**
 * 被装饰的抽象基类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午1:50:42
 */
public abstract class Humburger {

	protected String name;

	public String getName() {
		return name;
	}

	public abstract double getPrice();

}
