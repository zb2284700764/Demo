package com.demo.reflect;

/**
 * 反射
 * @author zhoubin
 *
 * @createDate 2017年9月8日 下午3:19:03
 */
public class ExtendType extends Type {
	public int pubIntExtendField;
	public String pubStringExtendField;
	private int prvIntExtendField;

	public ExtendType() {
		log("Default Constructor");
	}

	ExtendType(int arg1, String arg2) {
		pubIntExtendField = arg1;
		pubStringExtendField = arg2;

		log("Constructor with parameters");
	}

	public void setIntExtendField(int field7) {
		this.prvIntExtendField = field7;
	}

	public int getIntExtendField() {
		return prvIntExtendField;
	}

	private void log(String msg) {
		System.out.println("ExtendType:" + msg);
	}
	
	public void sayHello(String str) {
		System.out.println("Hello " + str);
	}
}
