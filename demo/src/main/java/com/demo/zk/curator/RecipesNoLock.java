package com.demo.zk.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 没有分布式锁的情况
 * 
 * @author zhoubin
 *
 * @createDate 2017年8月7日 下午2:04:46
 */
public class RecipesNoLock {
	
	public static void main(String[] args) {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						countDownLatch.await();
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String orderNo = sdf.format(new Date());
					System.out.println("订单号: "+orderNo);
				}
				
			}).start();
		}
		countDownLatch.countDown();
	}
	
}
