package com.demo.zk.config;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 *
 * @author zhoubin
 * @createDate 2016年4月7日 下午11:16:04
 */
public class MyClient implements Watcher {

	public String zkURL = "172.23.176.68:2181";
	public String root = "/myConfig";

	public String urlNode = root + "/url";
	public String usernameNode = root + "/username";
	public String passwordNode = root + "/password";
	// 加密
	public static String auth_type = "digest";
	public static String auth_passwd = "password";

	private String url;
	private String username;
	private String password;

	private ZooKeeper zooKeeper = null;

	public MyClient() {
	}

	public void init() {
		try {
			// 获取数据的同时，再设置上监听，要不Watcher在每次触发之后就失效了
			url = new String(zooKeeper.getData(urlNode, true, null));
			username = new String(zooKeeper.getData(usernameNode, true, null));
			password = new String(zooKeeper.getData(passwordNode, true, null));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	public ZooKeeper getZookper(){
		try {
			zooKeeper = new ZooKeeper(zkURL, 3000, this);
			zooKeeper.addAuthInfo(auth_type, auth_passwd.getBytes());
			while (zooKeeper.getState() != ZooKeeper.States.CONNECTED) {
				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("连接服务器成功");
		return zooKeeper;
	}

	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == Watcher.Event.EventType.None){
			System.out.println("连接服务器成功！");
		} else if(event.getType() == Watcher.Event.EventType.NodeCreated){
			System.out.println("节点创建成功！");
		} else if(event.getType() == Watcher.Event.EventType.NodeChildrenChanged){
			System.out.println("子节点更新成功！");
			init();
		} else if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
			System.out.println("节点更新成功！");
			init();
		} else if(event.getType() == Watcher.Event.EventType.NodeDeleted){
			System.out.println("节点删除成功！");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MyClient mc = new MyClient();
		ZooKeeper zk = mc.getZookper();
		mc.init();
		
		
		int i = 0;
		
		while(true){
			System.out.println("第【"+(i+1)+"】次获取");
			System.out.println(mc.getUrl());
			System.out.println(mc.getUsername());
			System.out.println(mc.getPassword());
			System.out.println("################################################");
			i++;
			if(i == 10){
				break;
			}
			Thread.sleep(10000);
		}
		
		zk.close();
	}
	
	
	
	// get set 
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
