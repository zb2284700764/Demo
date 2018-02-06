package com.demo.design_pattern.strategy.duck.test;

import com.demo.design_pattern.strategy.duck.duck.Duck;
import com.demo.design_pattern.strategy.duck.duck.MallardDuck;
import com.demo.design_pattern.strategy.duck.duck.ModelDuck;
import com.demo.design_pattern.strategy.duck.fly.FlyNoWay;
import com.demo.design_pattern.strategy.duck.fly.FlyRocketPowered;
import com.demo.design_pattern.strategy.duck.fly.FlyWithWings;
import com.demo.design_pattern.strategy.duck.quack.Quack;
import com.demo.design_pattern.strategy.duck.quack.Squeak;

/**
 * 测试
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:52:00
 */
public class MiniDuckSimulator {
	public static void main(String[] args) {
		Duck mallard = new MallardDuck(new FlyWithWings(), new Squeak());
		mallard.display();
		mallard.fly();
		mallard.quack();

		System.out.println("------------------");

		Duck model = new ModelDuck(new FlyNoWay(), new Quack());
		model.display();
		model.fly();
		model.quack();
		// 让不会飞的模型鸭子使用助推器
		model.setFly(new FlyRocketPowered());
		model.fly();
	}
}
