package org.carrot.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.carrot.domain.Account;
import org.carrot.repository.AccountDao;
import org.carrot.service.exception.ErrorCode;
import org.carrot.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Digests;
import org.springside.modules.utils.Encodes;
import org.springside.modules.utils.Ids;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

// Spring Bean的标识.
@Service
public class AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountDao accountDao;

	// 注入配置值
	@Value("${app.loginTimeoutSecs:600}")
	private int loginTimeoutSecs;

	// codehale metrics
	@Autowired
	private CounterService counterService;

	// guava cache
	private Cache<String, Account> loginUsers;

	@PostConstruct
	public void init() {
		loginUsers = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(loginTimeoutSecs, TimeUnit.SECONDS)
				.build();
	}

	@Transactional(readOnly = true)
	public String login(String email, String password) {
		Account account = accountDao.findByEmail(email);

		if (account == null) {
			throw new ServiceException("用户不存在", ErrorCode.UNAUTHORIZED);
		}

		if (!account.hashPassword.equals(hashPassword(password))) {
			throw new ServiceException("密码错误", ErrorCode.UNAUTHORIZED);
		}

		String token = Ids.uuid2();
		loginUsers.put(token, account);
		counterService.increment("loginUser");
		return token;
	}


	public void logout(String token) {
		Account account = loginUsers.getIfPresent(token);
		if (account == null) {
			logger.warn("logout an alreay logout token:" + token);
		} else {
			loginUsers.invalidate(token);
			counterService.decrement("loginUser");
		}
	}

	public Account getLoginUser(String token) {

		Account account = loginUsers.getIfPresent(token);

		if (account == null) {
			throw new ServiceException("用户没有登录", ErrorCode.UNAUTHORIZED);
		}

		return account;
	}

	@Transactional
	public void register(String email, String name, String password) {

		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
			throw new ServiceException("格式错误", ErrorCode.BAD_REQUEST);
		}

		Account account = new Account();
		account.email = email;
		account.name = name;
		account.hashPassword = hashPassword(password);
		accountDao.save(account);
	}
	@Transactional
	public void reset(String email, String password) {

		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
			throw new ServiceException("格式错误", ErrorCode.BAD_REQUEST);
		}

		Account account = accountDao.findByEmail(email);
		if (account == null) {
			throw new ServiceException("用户不存在", ErrorCode.UNAUTHORIZED);
		}
		account.hashPassword = hashPassword(password);
		accountDao.save(account);
	}

	protected static String hashPassword(String password) {
		return Encodes.encodeBase64(Digests.sha1(password));
	}
}
