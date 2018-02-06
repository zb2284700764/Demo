package com.demo.zk.watcher;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.demo.zk.common.IP;

/**
 *
 * @author zhoubin
 * @createDate 2016年4月4日 下午9:13:11
 */
public class Test01 {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_300000, new DemoWatcher());

		String node = "/app1";
		Stat stat = zk.exists(node, false);// 检测/app1是否存在
		if (stat == null) {
			// 创建节点
			String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
			System.out.println("result-->"+createResult);
		} else {
			zk.delete(node, 0);
		}
		// 获取节点的值 -->
		byte[] b = zk.getData(node, false, stat);
		System.out.println(new String("b--->"+b));
		zk.close();
	}
}
