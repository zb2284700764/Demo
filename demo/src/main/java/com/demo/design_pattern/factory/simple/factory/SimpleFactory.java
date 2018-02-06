package com.demo.design_pattern.factory.simple.factory;

import com.demo.design_pattern.factory.simple.product.IProduct;
import com.demo.design_pattern.factory.simple.product.ProductA;
import com.demo.design_pattern.factory.simple.product.ProductB;

/**
 * 简单工厂
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午9:55:42
 */
public class SimpleFactory {

	/**
	 * 创建产品实例
	 * 
	 * @param clazz
	 * @return
	 * @author zhoubin
	 * @createDate 2017年7月13日 下午10:01:22
	 */
	public IProduct create(Class<?> clazz) {
		if (clazz.getName().equals(ProductA.class.getName())) {
			return new ProductA();
		} else if (clazz.getName().equals(ProductB.class.getName())) {
			return new ProductB();
		}
		return null;
	}

}
