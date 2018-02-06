package com.demo.design_pattern.strategy.duck.fly;

/**
 * 不会飞(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:26:14
 */
public class FlyNoWay implements IFly {

	@Override
	public void fly() {
		System.out.println("不会飞");
	}

}
