package com.demo.design_pattern.strategy.duck.fly;

/**
 * 使用火箭助推器进行飞行(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:42:32
 */
public class FlyRocketPowered implements IFly {

	@Override
	public void fly() {
		System.out.println("可以使用火箭助推器进行飞行");

	}

}
