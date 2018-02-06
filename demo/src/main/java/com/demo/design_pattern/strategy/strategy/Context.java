package com.strategy.strategy;

/**
 * 环境角色类(可以理解为组装使用策略的类)
 * 持有一个策略类的引用，最终给客户端用的
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午9:54:58
 */
public class Context {
	// 定义一个抽象策略
	private IStrategy strategy;

	/**
	 * 构造方法，传入一个具体的策略实现类
	 * 
	 * @param strategy
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午9:56:26
	 */
	public Context(IStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 策略方法
	 * 
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午9:57:30
	 */
	public void strategy() {
		strategy.strategy();
	}

}
