package com.demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射测试类
 * @author zhoubin
 *
 * @createDate 2017年9月8日 下午3:22:00
 */
public class ReflectTest {

	public static void main(String[] args) {

		Class<?> c = ExtendType.class;

		/*
		 * 使用 getDeclaredFields 和 getFields 获取字段进行对比
		 * getFields 获取的是为public的属性，包括父类中定义，
		 * getDeclaredFields 获取的是指定类定义的所有定义的属性，不包括父类的。
		 */
		Field[] fieldArray1 = c.getDeclaredFields();
		for (Field field : fieldArray1) {
			System.out.println(field);
		}
		System.out.println("*************************************************************************************");
		Field[] fieldArray2 = c.getFields();
		for (Field field : fieldArray2) {
			System.out.println(field);
		}

		System.out.println("\n-------------------------------------------------------------------------------------\n");
		
		// 获取 方法
		Method[] m1 = c.getDeclaredMethods();
		for (Method method : m1) {
			System.out.println(method);
		}
		System.out.println("*************************************************************************************");
		Method[] m2 = c.getMethods();
		for (Method method : m2) {
			System.out.println(method);
		}

		System.out.println("\n-------------------------------------------------------------------------------------\n");
		
		// 使用getConstructors获取构造器
		Constructor<?>[] constructors = c.getConstructors();
		for (Constructor<?> m : constructors) {
			System.out.println(m);
		}
		System.out.println("*************************************************************************************");
		// 使用getDeclaredConstructors获取构造器
		constructors = c.getDeclaredConstructors();
		for (Constructor<?> m : constructors) {
			System.out.println(m);
		}

	}

}
