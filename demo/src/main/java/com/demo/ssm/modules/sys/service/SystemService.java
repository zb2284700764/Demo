package com.demo.ssm.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.ssm.common.security.Digests;
import com.demo.ssm.common.service.BaseService;
import com.common.util.Encodes;
import com.demo.ssm.modules.sys.dao.UserDao;
import com.demo.ssm.modules.sys.entity.User;

/**
 *
 * @author zhoubin
 *
 * @createDate 2017年7月6日 上午10:42:17
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {

	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 2; // 散列次数
//	public static final String HASH_ALGORITHM = "SHA-1";
//	public static final int HASH_INTERATIONS = 1024; // 散列次数
	public static final int SALT_SIZE = 8; // 盐的长度
	

	@Autowired
	private UserDao userDao;
	
	/**
	 * 根据登录名获取用户信息
	 * @Title getByLoginName
	 * @require 
	 * @param loginName
	 * @return 
	 * @throws 
	 * @author zhoubin
	 * @date 2017年7月6日 下午4:55:50
	 * @history
	 */
	public User getByLoginName(String loginName) {
		
		return userDao.getUserByLoginName(loginName);
	}



	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}









}

