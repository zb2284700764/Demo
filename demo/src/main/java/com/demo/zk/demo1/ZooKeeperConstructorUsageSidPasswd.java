package com.demo.zk.demo1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * 使用sessionid和密码创建可复用的zookeeper会话
 * @author zhoubin
 * @createDate 2016年4月25日 上午9:23:29
 */
public class ZooKeeperConstructorUsageSidPasswd implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event：" + event);
		if(KeeperState.SyncConnected == event.getState()){
			connectedSemaphore.countDown();
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZooKeeperConstructorUsageSidPasswd());
		connectedSemaphore.await();
		long sessionId = zooKeeper.getSessionId();
		byte[] password = zooKeeper.getSessionPasswd();
		
		// 使用错误的sessionId和密码创建 zookeeper会话
		zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZooKeeperConstructorUsageSidPasswd(), 1L, "test".getBytes());
				
		zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZooKeeperConstructorUsageSidPasswd(), sessionId, password);
		
		
		Thread.sleep(Integer.MAX_VALUE);
		
	}
}

