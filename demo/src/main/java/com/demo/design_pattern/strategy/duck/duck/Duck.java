package com.demo.design_pattern.strategy.duck.duck;

import com.demo.design_pattern.strategy.duck.fly.IFly;
import com.demo.design_pattern.strategy.duck.quack.IQuack;

/**
 * 鸭子的抽象类，方便以后再创建新类型鸭子
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:32:28
 */
public abstract class Duck {

	private IFly fly; // 飞的策略
	private IQuack quack; // 叫的策略

	public Duck() {
	}

	/**
	 * 展示自己颜色的方法
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:34:13
	 */
	public abstract void display();

	/**
	 * 所有鸭子都会游泳
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:46:01
	 */
	public void swim() {
		System.out.println("游泳");
	}

	/**
	 * 所有鸟都会吃的方法
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:37:08
	 */
	public void eat() {
		System.out.println("吃食物");
	}

	/**
	 * 飞
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:35:22
	 */
	public void fly() {
		fly.fly();
	}

	/**
	 * 叫
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:35:27
	 */
	public void quack() {
		quack.quack();
	}

	
	// set

	public void setFly(IFly fly) {
		this.fly = fly;
	}

	public void setQuack(IQuack quack) {
		this.quack = quack;
	}

}
