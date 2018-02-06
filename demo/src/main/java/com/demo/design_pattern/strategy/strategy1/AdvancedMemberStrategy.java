package com.demo.design_pattern.strategy.strategy1;

/**
 * 高级会员折扣类(高级会员给 8折)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:06:12
 */
public class AdvancedMemberStrategy implements IMemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {

		System.out.println("对于中级会员的折扣为: 8折");
		return booksPrice * 0.8;
	}

}
