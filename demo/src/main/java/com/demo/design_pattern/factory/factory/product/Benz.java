package com.demo.design_pattern.factory.factory.product;

/**
 * 具体产品(奔驰)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:26:57
 */
public class Benz extends Car {

	@Override
	public void drive() {
		System.out.println(this.getName() + "----go-----------------------");
	}

}
