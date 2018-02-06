package com.demo.design_pattern.strategy.strategy1;

/**
 * 中级会员折扣类(中级会员给 9折)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:04:40
 */
public class IntermediateMemberStrategy implements IMemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {

		System.out.println("对于中级会员的折扣为: 9折");
		return booksPrice * 0.9;
	}

}
