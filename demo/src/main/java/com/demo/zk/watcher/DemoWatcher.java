package com.demo.zk.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 *
 * @author zhoubin
 * @createDate 2016年4月4日 下午9:14:51
 */
public class DemoWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		System.out.println("----------->");
		System.out.println("path:" + event.getPath());
		System.out.println("type:" + event.getType());
		System.out.println("stat:" + event.getState());
		System.out.println("<-----------");
	}

}
