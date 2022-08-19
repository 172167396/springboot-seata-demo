package cn.tedu.storage.service;

import cn.tedu.storage.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "AT")
@Service
public class StorageATServiceImpl implements StorageService {

    @Resource
    StorageMapper storageMapper;

    @Override
    @Transactional
    public void decrease(String productId, BigDecimal count) {
        storageMapper.decrease(productId, count);
    }

}
