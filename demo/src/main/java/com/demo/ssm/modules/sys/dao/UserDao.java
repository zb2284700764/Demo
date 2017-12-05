package com.demo.ssm.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.ssm.common.persistence.CrudDao;
import com.demo.ssm.common.persistence.annotation.MyBatisDao;
import com.demo.ssm.modules.sys.entity.User;


/**
 * 
 * @ClassName: UserDao
 * @author zhoubin
 * @createDate 2017年6月30日 下午3:45:33
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
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
	public User getUserByLoginName(@Param("loginName")String loginName);
	
	/**
	 * 查询所有用户
	 * @Title findAllUser
	 * @require 
	 * @return 
	 * @throws 
	 * @author zhoubin
	 * @date 2017年9月18日 下午5:24:03
	 * @history
	 */
	public List<User> findAllUser();
	
}