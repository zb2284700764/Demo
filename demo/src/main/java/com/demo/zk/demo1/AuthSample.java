package com.demo.zk.demo1;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.demo.zk.common.IP;

/**
 * 给节点增加权限控制
 * @author zhoubin
 *
 * @createDate 2017年8月4日 下午3:12:11
 */
public class AuthSample {

	public static void main(String[] args) throws Exception {
		
		// 创建 zookeeper 连接
		ZooKeeper zk = new ZooKeeper(IP.ipAddress, IP.sessionTimeout_3000, null);
		
		// 创建连接之后添加权限信息
		zk.addAuthInfo("digest", "foo:true".getBytes());
		
		// 创建节点
		zk.create("/auth_test", "123".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		
		Thread.sleep(Integer.MAX_VALUE);
	}
	
}

