package com.demo.proxy.DynamicProxy.jdk.demo2;

import com.demo.proxy.DynamicProxy.jdk.demo2.MyInvocationHandler;
import com.demo.proxy.DynamicProxy.jdk.demo2.UserService;
import com.demo.proxy.DynamicProxy.jdk.demo2.UserServiceImpl;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年9月13日 下午2:47:31
 */
public class DynamicProxyTest {

	@Test
	public void test() {

		// 真实对象
		UserService userService = new UserServiceImpl();
		
		// 代理
		MyInvocationHandler handler = new MyInvocationHandler(userService);
		
		// 通过代理创建的代理对象
		UserService proxyUserService = (UserService) handler.getProxy();

		System.out.println(proxyUserService.getClass().getName());
		// 代理对象调用真实对象的方法
		proxyUserService.add();

		// 将产生的代理对象输出生成 class 文件，反编译进行查看，$Proxy0.class 这个 class 文件要先手动创建好
		String path = "D:/$Proxy0.class";
		byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", UserServiceImpl.class.getInterfaces());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			out.write(classFile);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
