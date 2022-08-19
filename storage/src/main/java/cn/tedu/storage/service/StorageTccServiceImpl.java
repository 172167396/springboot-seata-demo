package cn.tedu.storage.service;

import cn.tedu.storage.tcc.StorageTccAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "TCC")
@Service
public class StorageTccServiceImpl implements StorageService {


    @Resource
    private StorageTccAction storageTccAction;

    @Override
    public void decrease(String productId, BigDecimal count) {
        boolean success = storageTccAction.prepareDecreaseStorage(null, productId, count);
        if (success) {
            log.info("库存第一阶段锁定资源成功");
        }
    }

}
