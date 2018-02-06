package com.demo.zk.config;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 *
 * @author zhoubin
 * @createDate 2016年4月7日 下午10:42:41
 */
public class SetConfig {
	
	public static String url = "172.23.176.68:2181";
	public static String root = "/myConfig";
	
	public static String urlNode = root + "/url";
	public static String usernameNode = root + "/username";
	public static String passwordNode = root + "/password";

	// 加密
	public static String auth_type = "digest";
	public static String auth_passwd = "password";
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zooKeeper = new ZooKeeper(url, 3000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				System.out.println("触发了事件：" + event.getType());
//				System.out.println("事件状态："+event.getState());
			}
		});
		
		while (zooKeeper.getState() != ZooKeeper.States.CONNECTED) {
			Thread.sleep(1000);
		}
		zooKeeper.addAuthInfo(auth_type, auth_passwd.getBytes());
		
		
		if(zooKeeper.exists(root, true) == null){
			zooKeeper.create(root, "root".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		}
		
		if(zooKeeper.exists(urlNode, true) == null){
			zooKeeper.create(urlNode, "192.168.1.105:2181".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		}
		
		if(zooKeeper.exists(usernameNode, true) == null){
			zooKeeper.create(usernameNode, "username".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		}
		
		if(zooKeeper.exists(passwordNode, true) == null){
			zooKeeper.create(passwordNode, "password".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		}
		
		zooKeeper.close();
	}
}

