package com.demo.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.demo.zk.common.IP;

/**
 * Curator ZooKeeper 客户端工具使用
 * 
 * @author zhoubin
 *
 * @createDate 2017年8月4日 下午4:53:03
 */
public class CreateSessionSample {

	public static void main(String[] args) throws Exception {
		String path = "/test1";
		String path2 = "/hello/1/test";
		
		// 重试策略, 初始 sleep 时间, 最大重试次数
		RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);

//		CuratorFramework client = CuratorFrameworkFactory.newClient(IP.ipAddress, IP.sessionTimeout_300000, IP.connectedTimeout, rp);
//		// 开始连接
//		client.start();

		// 使用 fluent 风格的 API 来创建 ZooKeeper 会话
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(IP.ipAddress).sessionTimeoutMs(IP.sessionTimeout_3000).retryPolicy(rp).build();
		// 创建含隔离空间的会话
//		CuratorFrameworkFactory.builder().connectString(IP.ipAddress).sessionTimeoutMs(IP.sessionTimeout_3000).retryPolicy(rp).namespace("base").build();

		// 启动会话
		client.start();
		
		// 创建一个节点，初始内容为空，如果不设置节点属性，默认创建的是持久节点
		client.create().forPath(path);
		
		// 创建一个节点，附带设置节点属性 和 节点内容 并 递归创建父节点
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path2, "123".getBytes());
//		
		
		
		// 读取数据
		System.out.println(new String(client.getData().forPath(path2)));
		
		// 获取到节点的数据同时并获取到节点的状态
		Stat stat = new Stat();
		client.getData().storingStatIn(stat).forPath(path2);
		
		
		
		// 更新数据
		client.setData().forPath(path2, "456".getBytes());
		
		
		
		
		
//		// 删除一个节点
//		client.delete().forPath(path); // 只能删除叶子节点
//		
//		// 递归删除所有子节点
//		client.delete().deletingChildrenIfNeeded().forPath(path2);
//		
//		// 删除指定的版本
//		client.delete().withVersion(1).forPath(path);
//		
//		// 强制删除一个节点
//		client.delete().guaranteed().forPath(path);
		
		
		Thread.sleep(Integer.MAX_VALUE);
		
	}

}
