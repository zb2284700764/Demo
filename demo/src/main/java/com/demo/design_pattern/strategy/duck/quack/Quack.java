package com.demo.design_pattern.strategy.duck.quack;

/**
 * 叽叽叫(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:28:39
 */
public class Quack implements IQuack {

	@Override
	public void quack() {
		System.out.println("会叽叽喳喳的叫");
	}

}
