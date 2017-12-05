package com.demo.ssm.modules.sys.security.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表单注解,放在需要验证表单的方法上,一般是controller上, 和 ResubmitInterceptor 类一块儿使用
 * 
 * @author zhoubin
 *
 * @createDate 2017年6月7日 下午6:14:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface FormToken {
	boolean createToken() default false;
    boolean validateToken() default false;
}
