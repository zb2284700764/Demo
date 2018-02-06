package com.demo.design_pattern.strategy.duck.duck;

import com.demo.design_pattern.strategy.duck.fly.IFly;
import com.demo.design_pattern.strategy.duck.quack.IQuack;

/**
 * 模型鸭(具体的鸭子，组装飞、叫的策略)
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:50:02
 */
public class ModelDuck extends Duck {

	public ModelDuck(IFly fly, IQuack quack) {
		super.setFly(fly);
		super.setQuack(quack);
	}

	@Override
	public void display() {
		System.out.println("我是一只模型鸭");
	}

}

