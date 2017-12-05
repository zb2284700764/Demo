package com.demo.ssm.modules.sys.aop;

import com.common.util.IdGen;
import com.demo.ssm.modules.sys.security.interceptor.FormToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.common.util.StringUtils.isBlank;

/**
 * 防止 form 重复提交切面，通过aop的方式，在controller层用返回路径会有问题，第一次验证之后可以找到路径，第二次验证之后就找不到路径了
 * 
 * @author zhoubin
 *
 * @createDate 2017年6月7日 下午5:39:05
 */
// @Component
// @Aspect
public class ResubmitAspect {
	private static final String PARAM_TOKEN = "token";
	private static final String PARAM_TOKEN_FLAG = "TokenFlag_";

	/**
	 * 执行切面拦截逻辑
	 */
	@Around("@annotation(formToken)")
	public Object execute(ProceedingJoinPoint joinPoint, FormToken formToken) throws Throwable {

		if (formToken != null) {
			// 方法入参列表
			Object[] args = joinPoint.getArgs();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			// for (Object arg : args) {
			// // 方法入参是否包含request
			// if (arg != null && arg instanceof HttpServletRequest) {
			// HttpServletRequest request = (HttpServletRequest) arg;

			// 生成 token 并放入 session
			if (formToken.createToken()) {
				String token = IdGen.getRandomString(10);

				// 将 token 传入页面
				request.setAttribute(PARAM_TOKEN, token);

				// 将 token 放入缓存
				request.getSession().setAttribute(PARAM_TOKEN, token);

				// 继续执行目标对象的方法
				return joinPoint.proceed();
			}

			// 从 session 中删除 token
			if (formToken.validateToken()) {
				String clinetToken = request.getParameter(PARAM_TOKEN);
				if (!isRepeatSubmit(clinetToken, request)) {
					// 删除缓存中对应的 token
					request.getSession().removeAttribute(PARAM_TOKEN);

					// 继续执行目标对象的方法
					return joinPoint.proceed();
				}
			}
			// break;
			// }
			// }
		}
		return null;
	}

	/**
	 * 验证是否是重复提交
	 * 
	 * @Title isRepeatSubmit (方法名)
	 * @require
	 * @return
	 * @throws @author
	 *             zhoubin(作者)
	 * @date 2017年6月8日 上午10:49:10
	 * @history
	 */
	private boolean isRepeatSubmit(String clinetToken, HttpServletRequest request) {

		if (isBlank(clinetToken)) {
			return true;
		}

		// 从缓存中获取token
		String serverToken = (String) request.getSession().getAttribute(PARAM_TOKEN);
		if (isBlank(serverToken)) {
			return true;
		}

		if (!serverToken.equals(clinetToken)) {
			return true;
		}

		// 现在默认都是验证通过
		return false;
	}
}
