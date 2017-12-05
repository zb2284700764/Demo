package com.demo.ssm.modules.sys.security.interceptor;

import com.common.util.IdGen;
import com.common.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交表单的拦截器, 和 FormToken 类一起使用, FormToken 在 Controller 类中的方法上进行注解
 *
 * @author zhoubin
 * @createDate 2017年9月19日 下午2:59:25
 */
public class ResubmitInterceptor extends HandlerInterceptorAdapter {
    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TOKEN_FLAG = "TokenFlag_";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod(); // 得到拦截请求的方法对象
            FormToken formToken = method.getAnnotation(FormToken.class);
            if (formToken != null) {
                // 生成 token 并放入 session
                if (formToken.createToken()) {
                    String token = IdGen.getRandomString(10);

                    // 将 token 传入页面
                    request.setAttribute(PARAM_TOKEN, token);

                    // 将 token 放入缓存
                    request.getSession().setAttribute(PARAM_TOKEN, token);
                }

                // 从 session 中删除 token
                if (formToken.validateToken()) {
                    String clientToken = request.getParameter(PARAM_TOKEN);
                    if (isRepeatSubmit(clientToken, request)) {
                        System.out.println("重复提交");
                        return false;
                    }
                    // 删除缓存中对应的 token
                    request.getSession().removeAttribute(PARAM_TOKEN);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }


    /**
     * 验证是否是重复提交
     *
     * @return true 表示是重复提交, false 表示不是重复提交
     * @Title isRepeatSubmit (方法名)
     * @author zhoubin(作者)
     * @date 2017年6月8日 上午10:49:10
     * @history
     */
    private boolean isRepeatSubmit(String clientToken, HttpServletRequest request) {

        if (StringUtils.isBlank(clientToken)) {
            return true;
        }

        // 从缓存中获取token
        String serverToken = (String) request.getSession().getAttribute(PARAM_TOKEN);
        if (StringUtils.isBlank(serverToken)) {
            return true;
        }

        if (!serverToken.equals(clientToken)) {
            return true;
        }

        return false;
    }
}

