package com.demo.zk.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.demo.zk.common.IP;

/**
 * 没有分布式锁的情况
 * 
 * @author zhoubin
 *
 * @createDate 2017年8月7日 下午2:04:46
 */
public class RecipesLock {
	
	public static void main(String[] args) {
		String lockPath = "/curator_recipes_lock_path";
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(IP.ipAddress).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
		final InterProcessMutex lock = new InterProcessMutex(client, lockPath);
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						countDownLatch.await();
						lock.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					}
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String orderNo = sdf.format(new Date());
					System.out.println("订单号: "+orderNo);
					
					try {
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}).start();
		}
		countDownLatch.countDown();
	}
	
}
