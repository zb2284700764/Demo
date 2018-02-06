package com.strategy.strategy1;

import org.junit.Test;

/**
 * 客户端使用环境
 * 
 * @author zhoubin
 *
 * @createDate 2017年7月10日 下午10:09:42
 */
public class Client {

	@Test
	public void test() {
		// 创建要使用的策略(高级会员策略)
		IMemberStrategy memberStrategy = new AdvancedMemberStrategy();

		// 创建使用环境(可以理解为在计算书本价格的时候根据会员级别判断是使用哪种计算策略)
		Price price = new Price(memberStrategy);

		// 计算价格
		double quote = price.quote(108);
		System.out.println("图书的价格是：" + quote);
	}

}
