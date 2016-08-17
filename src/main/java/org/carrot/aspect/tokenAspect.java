package org.carrot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.carrot.domain.Account;
import org.carrot.service.AccountService;
import org.carrot.service.exception.ErrorCode;
import org.carrot.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xuan on 16/8/16.
 */
@Aspect
@Component
public class TokenAspect {
    private static final Logger logger = LoggerFactory.getLogger(TokenAspect.class);
    @Autowired
    private AccountService accountServcie;

    /**
     * 定义拦截规则：org.carrot.api包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* org.carrot.api..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void before() {
    }

    @Before("before() && args(token,..)")
    public void beforeAdvide(JoinPoint point, String token){
        //处理逻辑
        logger.info("token: {}", token);
        if (token == null) {
            throw new ServiceException("No token in request", ErrorCode.NO_TOKEN);
        }
        isLogin(token);
    }

    //判断是否已经登录
    private void isLogin(String token) {
        accountServcie.getLoginUser(token);
    }
}
