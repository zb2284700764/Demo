package com.demo.zk.demo1;

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
 * 使用异步获取子节点数据
 * @author Zb
 * @createDate 2016年4月26日 下午11:31:34
 */

public class ZookeeperGetDataAsyncUsage implements Watcher {
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
					System.out.println("节点内容变化后收到通知，再次获取节点内容并再次添加监听器");
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
		zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, new ZookeeperGetDataAsyncUsage());
		connectedSemaphore.await();
		

		// 初始化节点数据
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		// 获取节点内容并添加监听器用来监听节点内容是否有变化
		zk.getData(path, true, new IDataCallback(), null);
		// 更新节点内容
		zk.setData(path, "Hello World Test".getBytes(), -1);
		
		Thread.sleep(1000);
	}

}

class IDataCallback implements AsyncCallback.DataCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
		System.out.println("连接成功之后异步获取节点内容");
		System.out.println(rc + ", " + path + ", " + new String(data));
		System.out.println(stat.getCzxid() + ", " + stat.getMzxid() + ", " + stat.getVersion());
	}

}
