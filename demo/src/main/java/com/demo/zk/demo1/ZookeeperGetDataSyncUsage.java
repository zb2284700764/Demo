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
 * 使用同步获取子节点数据
 * @author Zb
 * @createDate 2016年4月26日 下午11:31:34
 */

public class ZookeeperGetDataSyncUsage implements Watcher {
	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	private static Stat stat = new Stat();

	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				connectedSemaphore.countDown();
			} else if(EventType.NodeDataChanged == event.getType()){
				try {
					// 节点内容变化后收到通知，再获取节点内容并再添加监听器
					System.out.println(new String(zk.getData(event.getPath(), true, stat)));
					System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String path = "/hello";
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperGetDataSyncUsage());
		connectedSemaphore.await();
		
		// 初始化节点数据
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		// 连接成功后同步获取节点内容，并添加监听器用来监听节点内容的变化
		System.out.println(new String(zk.getData(path, true, stat)));
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		
		// 更新节点内容
		zk.setData(path, "test".getBytes(), -1);
		Thread.sleep(1000);
	}

}
