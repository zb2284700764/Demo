package com.demo.design_pattern.strategy.strategy;

/**
 * 具体的策略实现类A
 * 包装了相关的算法和行为
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午9:53:01
 */
public class ConcreteStrategyA implements IStrategy {

	@Override
	public void strategy() {
		System.out.println("策略A ConcreteStrategyA");
	}

}
