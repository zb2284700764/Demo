package com.demo.zk.demo1;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.demo.zk.common.IP;

/**
 * 同步更新节点内容
 * @author zhoubin
 *
 * @createDate 2017年8月4日 上午10:05:19
 */
public class SetDaatAPISyncUsage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk;

	@Override
	public void process(WatchedEvent event) {
		// 连接着的状态
		if(KeeperState.SyncConnected == event.getState()) {
			System.out.println("event type: " +  event.getType());
			if(EventType.None == event.getType() && null == event.getPath()) {
				// 释放阻塞的线程
				connectedSemaphore.countDown();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		String path = "/hello";
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new SetDaatAPISyncUsage());

		// 主线程在此阻塞
		connectedSemaphore.await();
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		// 获取数据
		System.out.println("获取的数据"+new String(zk.getData(path, true, null)));

		// 更新数据
		Stat stat = zk.setData(path, "456".getBytes(), -1);
		System.out.println("Czxid: "+stat.getCzxid()+"; Mzxid: "+stat.getMzxid()+"; version: "+stat.getVersion());

		Stat stat2 = zk.setData(path, "789".getBytes(), stat.getVersion());
		System.out.println("Czxid: "+stat2.getCzxid()+"; Mzxid: "+stat2.getMzxid()+"; version: "+stat2.getVersion());
		
		try {
			zk.setData(path, "147".getBytes(), stat.getVersion());
		} catch (KeeperException e) {
			System.out.println("Error: "+e.code()+"; "+e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(Integer.MAX_VALUE);
	}
}

