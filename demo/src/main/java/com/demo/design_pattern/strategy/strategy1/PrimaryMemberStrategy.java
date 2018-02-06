package com.demo.design_pattern.strategy.strategy1;

/**
 * 初级会员折扣类(初级会员不给折扣)
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:02:38
 */
public class PrimaryMemberStrategy implements IMemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {

		System.out.println("对于初级会员没有折扣");
		return booksPrice;
	}

}
