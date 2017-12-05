package com.demo.ssm.modules.sys.security.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 修改请求参数的 Filter，继承 spring org.springframework.web.filter.OncePerRequestFilter
 * 这个 Filter，让这个 filter 在一次请求只执行一次
 * 
 * @author zhoubin
 *
 * @createDate 2017年9月22日 下午3:56:05
 */
public class ModifyParametersFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		ModifyParametersWrapper modifyParametersWrapper = new ModifyParametersWrapper(request);
		filterChain.doFilter(modifyParametersWrapper, response);
	}

	/**
	 * 继承HttpServletRequestWrapper 的装饰类，以达到修改HttpServletRequest参数的目的
	 * 
	 * @ClassName: ModifyParametersWrapper
	 * @author zhoubin
	 * @createDate 2017年9月22日 下午4:09:40
	 */
	private class ModifyParametersWrapper extends HttpServletRequestWrapper {
		// 所有请求参数的 map
		private Map<String, String[]> parametersMap;

		public ModifyParametersWrapper(HttpServletRequest request) {
			super(request);
			parametersMap = request.getParameterMap();
		}

		/**
		 * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值
		 */
		@Override
		public String getParameter(String name) {
			String[] resultArray = parametersMap.get(name);
			if (resultArray == null || resultArray.length <= 0)
				return null;
			else {
				System.out.println("修改之前： " + resultArray[0]);
				return modify(resultArray[0]);
			}
		}

		/**
		 * 获取所有参数名
		 */
		@Override
		public Enumeration<String> getParameterNames() {
			Vector<String> vector = new Vector<String>(parametersMap.keySet());
			return vector.elements();
		}

		/**
		 * 获取指定参数名的所有值的数组，如：checkbox的所有数据 
		 * 接收数组变量 ，如checkobx类型
		 */
		@Override
		public String[] getParameterValues(String name) {
			String[] resultArray = parametersMap.get(name);
			if (resultArray == null || resultArray.length <= 0)
				return null;
			else {
				int length = resultArray.length;
				for (int i = 0; i < length; i++) {
					System.out.println("修改之前2： " + resultArray[i]);
					resultArray[i] = modify(resultArray[i]);
				}
				return resultArray;
			}
		}

		/**
		 * 自定义的一个简单修改原参数的方法，即：给原来的参数值前面添加了一个修改标志的字符串
		 * 
		 * @param string 原参数值
		 * @return 修改之后的值
		 */
		private String modify(String string) {
			System.out.println("修改之后: Modified: " + string);
			return "Modified: " + string;
		}
	}

}
