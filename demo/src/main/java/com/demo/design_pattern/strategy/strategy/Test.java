package com.demo.design_pattern.strategy.strategy;

import com.demo.design_pattern.strategy.strategy1.AdvancedMemberStrategy;
import com.demo.design_pattern.strategy.strategy1.IMemberStrategy;
import com.demo.design_pattern.strategy.strategy1.Price;

/**
 * 客户端使用环境
 *
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:09:42
 */
public class Test {


	public static void main(String[] args) {

		// 创建要使用的策略
		IStrategy strategyA = new ConcreteStrategyA();

		// 根据具体的策略对象创建使用环境
		Context context = new Context(strategyA);

		// 使用环境调用具体的方法
		context.strategy();

	}


}
