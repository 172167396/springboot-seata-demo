package cn.tedu.account.service;

import cn.tedu.account.tcc.AccountTccAction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "TCC")
@Service
public class AccountTccServiceImpl implements AccountService {

    @Resource
    private AccountTccAction accountTccAction;

    @Override
    public void decrease(String userId, BigDecimal money) {
        accountTccAction.prepareDecreaseAccount(null, userId, money);
    }

}