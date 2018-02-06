package com.demo.design_pattern.decorator.demo3.decorator;

import com.demo.design_pattern.decorator.demo3.component.Humburger;

/**
 * 装饰者基类 配料类
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午1:54:16
 */
public abstract class Condiment extends Humburger {

	/**
	 * 重写被装饰者基类中的方法
	 */
	public abstract String getName(); 

}
