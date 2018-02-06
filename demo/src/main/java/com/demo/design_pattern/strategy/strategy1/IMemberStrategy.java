package com.demo.design_pattern.strategy.strategy1;

/**
 * 抽象折扣
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午9:59:30
 */
public interface IMemberStrategy {

	/**
	 * 计算图书的价格
	 * 
	 * @param booksPrice 图书原价
	 * @return 折扣价
	 * @author zhoubin
	 * @createDate 2017年7月10日 下午10:01:03
	 */
	public double calcPrice(double booksPrice);
}
