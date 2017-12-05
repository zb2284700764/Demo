package com.demo.ssm.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.ssm.modules.sys.dao.UserDao;
import com.demo.ssm.modules.sys.entity.User;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年6月30日 下午2:37:26
 */
@Service
public class UserService {
	
	public int a = 0;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 通过登录名获取用户信息
	 * @Title getUserByLoginName
	 * @require 
	 * @param loginName 登录名
	 * @return 
	 * @throws 
	 * @author zhoubin
	 * @date 2017年6月30日 下午4:12:33
	 * @history
	 */
	public User getUserByLoginName(String loginName) {
		
		return userDao.getUserByLoginName(loginName);
	}
	
	/**
	 * 查询所有用户
	 * @Title findAllUser
	 * @require 
	 * @return 
	 * @throws 
	 * @author zhoubin
	 * @date 2017年9月18日 下午5:26:30
	 * @history
	 */
	public List<User> findAllUser(){
		return userDao.findAllList();
	}
	
}

