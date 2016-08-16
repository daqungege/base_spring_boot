package org.carrot.api;

import org.carrot.domain.Money;
import org.carrot.service.MoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.constants.MediaTypes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
@RequestMapping(value = "/api/money")
public class MoneyEndPoint {

    private static Logger logger = LoggerFactory.getLogger(MoneyEndPoint.class);


    @Autowired
    private MoneyService moneyService;

    @RequestMapping(value = "/list", produces = MediaTypes.JSON_UTF_8)
    public List<Money> list(@RequestParam(value = "token") String token) {
        return moneyService.list();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Money add(@RequestParam(value = "token") String token, @RequestBody Money money) {
        return moneyService.add(token, money);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public Money update(@RequestParam(value = "token") String token, @PathVariable(value = "id") Long id, @RequestBody Money money) {
        return moneyService.update(id, money);
    }

    @RequestMapping(value = "/del/{id}", produces = MediaTypes.JSON_UTF_8)
    public Map<String, String> del(@RequestParam(value = "token") String token, @PathVariable(value = "id") Long id) {
        moneyService.del(id);
        return Collections.singletonMap("del", "true");
    }
}
