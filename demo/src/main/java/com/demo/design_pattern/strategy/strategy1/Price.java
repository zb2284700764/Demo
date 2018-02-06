package com.strategy.strategy1;

/**
 * 价格类
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:07:14
 */
public class Price {
	// 定义一个计算几个的策略
	private IMemberStrategy memberStrategy;

	/**
	 * 构造函数传入一个具体的策略对象
	 * 
	 * @param memberStrategy
	 */
	public Price(IMemberStrategy memberStrategy) {
		this.memberStrategy = memberStrategy;
	}

	/**
	 * 计算图书的价格
	 * 
	 * @param booksPrice 原来价格
	 * @return 折后价格
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:09:02
	 */
	public double quote(double booksPrice) {
		return this.memberStrategy.calcPrice(booksPrice);
	}
}
