package org.carrot.api;

import com.google.common.collect.Maps;
import org.carrot.domain.Account;
import org.carrot.service.AccountService;
import org.carrot.service.exception.ErrorCode;
import org.carrot.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.constants.MediaTypes;

import java.util.Collections;
import java.util.Map;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
@RequestMapping(value = "/api/login")
public class WishUserEndPoint {

    private static Logger logger = LoggerFactory.getLogger(WishUserEndPoint.class);


    @Autowired
    private AccountService accountServcie;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Map<String, String> login(@RequestBody SimpleAccount account) {

        if (StringUtils.isEmpty(account.email) || StringUtils.isEmpty(account.password)) {
            throw new ServiceException("用户名和密码不能为空", ErrorCode.BAD_REQUEST);
        }

        String token = accountServcie.login(account.email, account.password);
        Account trueAccount = accountServcie.getLoginUser(token);
        Map<String, String> retMap = Maps.newHashMap();
        retMap.put("token", token);
        retMap.put("name", trueAccount.name);
        return retMap;
    }

    @RequestMapping(value = "/sign-out", produces = MediaTypes.JSON_UTF_8)
    public Map<String, String> logout(@RequestParam(value = "token", required = false) String token) {
        accountServcie.logout(token);
        return Collections.singletonMap("logout", "true");
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Map<String, String> register(@RequestBody SimpleAccount account) {

        if (StringUtils.isEmpty(account.email) || StringUtils.isEmpty(account.name) || StringUtils.isEmpty(account.password)) {
            throw new ServiceException("User or name or password empty", ErrorCode.BAD_REQUEST);
        }

        accountServcie.register(account.email, account.name, account.password);
        return login(account);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Map<String, String> reset(@RequestBody SimpleAccount account) {
        if (StringUtils.isEmpty(account.email) || StringUtils.isEmpty(account.password)) {
            throw new ServiceException("用户名和密码不能为空", ErrorCode.BAD_REQUEST);
        }
        accountServcie.reset(account.email, account.password);
        return Collections.singletonMap("reset", "true");
    }

    static class SimpleAccount {
        public String email;
        public String name;
        public String password;
    }
}
