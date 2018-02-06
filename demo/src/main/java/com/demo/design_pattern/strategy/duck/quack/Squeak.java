package com.demo.design_pattern.strategy.duck.quack;

/**
 * 嘎嘎叫(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:29:39
 */
public class Squeak implements IQuack {

	@Override
	public void quack() {
		System.out.println("会嘎嘎的叫");
	}

}
