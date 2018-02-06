package com.demo.zk.demo1;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.demo.zk.common.IP;

/**
 * 异步更新节点内容
 * @author zhoubin
 *
 * @createDate 2017年8月4日 上午10:05:19
 */
public class SetDaatAPIASyncUsage implements Watcher {

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
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new SetDaatAPIASyncUsage());



		// 主线程在此阻塞
		connectedSemaphore.await();
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		// 更新数据
		zk.setData(path, "456".getBytes(), -1, new IStatCallback(), null);
		
//		System.out.println("Czxid: "+stat.getCzxid()+"; Mzxid: "+stat.getMzxid()+"; version: "+stat.getVersion());
		Thread.sleep(Integer.MAX_VALUE);
	}
}
class IStatCallback implements AsyncCallback.StatCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		System.out.println("rc: " + rc);
		if(rc == 0) {
			System.out.println("IStatCallback success");
		}
	}
	
}
