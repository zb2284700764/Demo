package com.demo.design_pattern.decorator.demo1.decorator;

import com.demo.design_pattern.decorator.demo1.component.Person;

/**
 * 由于不同的部位有不同的衣物，不能混为一谈，比如说，衣服、帽子、鞋子等，那么这里我们创建的Decorator为衣服和帽子 
 * 抽象装饰者-衣服
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月21日 下午5:32:15
 */
public abstract class ClothingDecorator extends Person {
	
	public abstract String getDescription();
	
}
