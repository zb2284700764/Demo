package com.demo.zk.demo1;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
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
 * 使用异步获取子节点路径
 * 
 * @author Zb
 * @createDate 2016年4月26日 下午11:10:00
 */

public class ZookeeperGetChildrenAsyncUsage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk = null;

	@Override
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				connectedSemaphore.countDown();
			} else if(event.getType() == EventType.NodeChildrenChanged){
				try {
					// 同步获取
					System.out.println("ReGet Child: " + zk.getChildren(event.getPath(), true));
					// 异步获取
					zk.getChildren(event.getPath(), true, new IChildren2Callback(), "I am context ctx");
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
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperGetChildrenAsyncUsage());

//		Stat stat = zk.exists(path, false);
//		if (stat != null) {
//			zk.delete(path, -1);
//		}
//
//		zk.create(path, "Hello world".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create(path + "/c1", "子节点1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

		zk.getChildren(path, true, new IChildren2Callback(), null);
		

		zk.create(path+"/c2", "子节点2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Thread.sleep(Integer.MAX_VALUE);
	}

}

class IChildren2Callback implements AsyncCallback.Children2Callback {

	@Override
	public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
		System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path + ", ctx: "
				+ ctx + ", children List: " + children + ", stat: " + stat);
	}

}
