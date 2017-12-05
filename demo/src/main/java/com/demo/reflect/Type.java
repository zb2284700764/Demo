package com.demo.reflect;

/**
 * 反射
 * @author zhoubin
 *
 * @createDate 2017年9月8日 下午3:18:21
 */
public class Type {

	public int pubIntField;
	public String pubStringField;
	private int prvIntField;

	public Type() {
		log("Default Constructor");
	}

	Type(int arg1, String arg2) {
		pubIntField = arg1;
		pubStringField = arg2;

		log("Constructor with parameters");
	}

	public void setIntField(int val) {
		this.prvIntField = val;
	}

	public int getIntField() {
		return prvIntField;
	}

	private void log(String msg) {
		System.out.println("Type:" + msg);
	}

}
