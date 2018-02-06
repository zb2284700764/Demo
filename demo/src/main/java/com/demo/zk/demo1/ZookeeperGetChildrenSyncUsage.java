package com.demo.zk.demo1;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * 使用同步获取子节点路径
 * @author Zb
 * @createDate 2016年4月26日 下午10:46:12
 */

public class ZookeeperGetChildrenSyncUsage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	
	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				connectedSemaphore.countDown();
			}
			// 当节点有变化的时候通知客户端，仅仅是 通知客户端节点发生变化了，不包含最新的节点列表
			else if(event.getType() == EventType.NodeChildrenChanged){
				try {
					// 重新获取所有节点，重新获取之后再次注册一下 Watcher 是因为 Watcher 通知是一次性的，所以需要客户端反复注册该通知
					System.out.println("ReGet Child: " + zk.getChildren(event.getPath(), true));
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
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperGetChildrenSyncUsage());
		connectedSemaphore.await();
		zk.create(path, "Hello World".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create(path+"/c1", "子节点1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		// 获取根节点之后创建监听，用来订阅当前节点列表的变化通知
		List<String> childrenList = zk.getChildren(path, true);
		System.out.println(childrenList);
		
		// 创建监听之后再新建节点
		zk.create(path+"/c2", "子节点2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Thread.sleep(Integer.MAX_VALUE);
	}

}
