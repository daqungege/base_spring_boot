package org.carrot.service;

import com.google.common.collect.Lists;
import org.carrot.domain.Money;
import org.carrot.repository.MoneyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// Spring Bean的标识.
@Service
public class MoneyService {

    private static Logger logger = LoggerFactory.getLogger(MoneyService.class);

    @Autowired
    private MoneyDao moneyDao;

    @Autowired
    private AccountService accountService;

    public List<Money> list() {
        return Lists.newArrayList(moneyDao.findAll());
    }

    public Money add(String token, Money money) {
        money.account = accountService.getLoginUser(token);
        System.out.println(money);
        return moneyDao.save(money);
    }

    public Money update(Long id, Money money) {
        Money storeMoney = moneyDao.findOne(id);
        // ['name', 'type', 'platform', 'money', 'expectRevenueMin', 'expectRevenueMax', 'startTime',
        // 'endTime', 'realRevenue', 'actualRevenue', 'status']
        storeMoney.name = money.name;
        storeMoney.type = money.type;
        storeMoney.platform = money.platform;
        storeMoney.money = money.money;
        storeMoney.expectRevenueMin = money.expectRevenueMin;
        storeMoney.expectRevenueMax = money.expectRevenueMax;
        storeMoney.startTime = money.startTime;
        storeMoney.endTime = money.endTime;
        storeMoney.realRevenue = money.realRevenue;
        storeMoney.actualRevenue = money.actualRevenue;
        storeMoney.status = money.status;
        storeMoney.updateTime = new Date();
        return moneyDao.save(storeMoney);
    }

    public void del(Long id) {
        moneyDao.delete(id);
    }
}
