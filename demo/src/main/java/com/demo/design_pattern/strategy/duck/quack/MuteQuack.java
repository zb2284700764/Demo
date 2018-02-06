package com.demo.design_pattern.strategy.duck.quack;

/**
 * 安安静静不叫(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:30:37
 */
public class MuteQuack implements IQuack {

	@Override
	public void quack() {
		System.out.println("安安静静的不叫");
	}

}
