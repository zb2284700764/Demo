package com.demo.design_pattern.factory.simple;

import com.demo.design_pattern.factory.simple.factory.SimpleFactory;
import com.demo.design_pattern.factory.simple.product.ProductA;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年7月13日 下午10:07:01
 */
public class Test {

	public static void main(String[] args) {
		SimpleFactory factory = new SimpleFactory();
		ProductA product = (ProductA) factory.create(ProductA.class);
		product.production();		
	}

}

