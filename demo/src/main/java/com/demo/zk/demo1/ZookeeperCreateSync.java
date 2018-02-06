package com.demo.zk.demo1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * 同步的方式创建节点
 * @author zhoubin
 * @createDate 2016年4月25日 下午11:20:26
 */
public class ZookeeperCreateSync implements Watcher {
	
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			connectedSemaphore.countDown();
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperCreateSync());
		
		connectedSemaphore.await();
		
//		zooKeeper.delete("/hello", -1);
		
		// 创建临时节点
		String path1 = zooKeeper.create("/hello", "Hello World".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("Success create znode：" + path1);
		
		// 创建临时顺序节点
		String path2 = zooKeeper.create("/hello2", "Hello world".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("Success create znode："+path2);
		
		// 创建持久节点
		String path3 = zooKeeper.create("/hello3", "Hello World".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(path3);
	}
	
}

