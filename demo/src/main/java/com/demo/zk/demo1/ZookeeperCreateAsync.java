package com.demo.zk.demo1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * 异步的方式创建节点
 * @author zhoubin
 * @createDate 2016年4月25日 下午11:44:37
 */
public class ZookeeperCreateAsync implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			System.out.println("countdown()");
			connectedSemaphore.countDown();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperCreateAsync());
		System.out.println("await()");
		connectedSemaphore.await();
		zooKeeper.create("/hello", "Hello World".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new IStringCallback(), "I am context");
		zooKeeper.create("/hello", "Hello World".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I am context");
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}
class IStringCallback implements AsyncCallback.StringCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("Create path result：【" + rc + "," + path + "," + ctx + "," + name + "】");
	}
	
}

