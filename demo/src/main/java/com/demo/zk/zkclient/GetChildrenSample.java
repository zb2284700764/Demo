package com.demo.zk.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import com.demo.zk.common.IP;

/**
 * 使用 ZkClient 监听子节点列表变更
 * @author zhoubin
 *
 * @createDate 2017年8月4日 下午3:46:17
 */
public class GetChildrenSample {

	public static void main(String[] args) throws Exception {
		String path = "/hello";
		String path2 = "/hello/test1";

		ZkClient zkClient = new ZkClient(IP.ipAddress, IP.sessionTimeout_3000);
		
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println(parentPath+"节点发生变化，当前节点是："+currentChilds);
			}
		});

		
		// 创建瞬时节点
		zkClient.createPersistent(path, "123");
		Thread.sleep(1000);
		System.out.println(zkClient.getChildren(path));
		Thread.sleep(1000);
		zkClient.createPersistent(path2, "456");
		Thread.sleep(1000);
		System.out.println("节点: "+zkClient.getChildren(path)+"; 数据: "+zkClient.readData(path));
		zkClient.delete(path2);
		Thread.sleep(1000);
		zkClient.delete(path);
		
	}

}

