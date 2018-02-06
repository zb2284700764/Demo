package com.demo.design_pattern.strategy.duck.fly;

/**
 * 有翅膀会飞(具体策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:24:15
 */
public class FlyWithWings implements IFly {

	@Override
	public void fly() {
		System.out.println("会飞");
	}

}
