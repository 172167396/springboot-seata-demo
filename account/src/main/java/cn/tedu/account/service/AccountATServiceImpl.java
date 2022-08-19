package cn.tedu.account.service;

import cn.tedu.account.mapper.AccountMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "AT")
@Service
public class AccountATServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;

    @Override
    public void decrease(String userId, BigDecimal money) {
        accountMapper.decrease(userId, money);
    }

}