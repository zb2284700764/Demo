package com.demo.design_pattern.factory.factory.product;

/**
 * 具体产品(宝马)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:27:32
 */
public class Bmw extends Car {

	@Override
	public void drive() {
		System.out.println(this.getName() + "----go-----------------------");
	}

}
