package com.demo.zk.demo1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * Zookeeper 创建一个会话
 * @author zhoubin
 * @createDate 2016年4月24日 下午2:15:14
 */
public class ZooKeeperConstructorUsageSimple implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	
	
	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZooKeeperConstructorUsageSimple());
		System.out.println(zooKeeper.getState());
		try {
			// 主程序在此位置阻塞，等待服务器端发来的SyncConnected事件，直到其他线程完成各自的任务调用 CountDownLatch.countDown() 将初始化的 count 减少为 0 之后，才能继续下面的代码执行
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Zookeeper session established.");
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event：" + event);
		System.out.println("状态:" + event.getState()+":"+event.getType()+":"+event.getWrapper()+":"+event.getPath());
		if(KeeperState.SyncConnected == event.getState()){

			// 删除主程序在 CountDownLatch 上的等待阻塞
			connectedSemaphore.countDown();
		}
	}

}

