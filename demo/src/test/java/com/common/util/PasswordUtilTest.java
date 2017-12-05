package com.common.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.demo.ssm.common.security.Digests;

//@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration(locations = { "classpath:spring-context.xml"})
public class PasswordUtilTest {

	

	@Test
	public void testEntryptPassword() {
//		System.out.println(PasswordUtil.entryptPassword("250031&gt;"));
		
		System.out.println(PasswordUtil.entryptPasswordMD5("12345"));
		System.out.println(PasswordUtil.entryptPasswordSHA1("123456"));

	}

	public void testValidatePassword() {
		assertEquals(true, PasswordUtil.validatePassword("123456", "74dfcefa8cf9a5fd7eae4552cc1b47546ee7b9b74fb3c78a71eb061a"));
		assertEquals(true, PasswordUtil.validatePassword("123456", "7c04e848ae0659ea503f16039ecdc4fb1eea895a1cb3a0162bc12fc4"));
		assertEquals(true, PasswordUtil.validatePassword("123456", "96aaabcd7900f80edb79b77c01fba62b6ceb0a3e69823ea40e5315b0"));
		assertEquals(true, PasswordUtil.validatePassword("123456", "f4a068c8df644fa23614d51b1c1bd22fe0951ba64d1045e54270da2f"));
	}

	public void testt() {
		String str = "hello world";
		byte[] b = SerializationUtils.serialize(str);
		
		System.out.println(DigestUtils.sha1Hex(b));
		System.out.println(Encodes.encodeHex(Digests.sha1(b)));
	}
}

