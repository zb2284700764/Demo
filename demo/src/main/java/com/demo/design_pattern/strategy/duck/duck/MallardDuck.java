package com.demo.design_pattern.strategy.duck.duck;

import com.demo.design_pattern.strategy.duck.fly.IFly;
import com.demo.design_pattern.strategy.duck.quack.IQuack;

/**
 * 野鸭子(具体的鸭子，组装飞、叫的策略)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:47:16
 */
public class MallardDuck extends Duck implements IFly {

	public MallardDuck(IFly fly, IQuack quack) {
		super.setFly(fly);
		super.setQuack(quack);
	}

	@Override
	public void display() {
		System.out.println("我是一只野鸭子");
	}

}
