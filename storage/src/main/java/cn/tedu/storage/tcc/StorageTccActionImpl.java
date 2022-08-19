package cn.tedu.storage.tcc;

import cn.tedu.storage.entity.Storage;
import cn.tedu.storage.mapper.StorageMapper;
import com.tcc.tcccommon.utils.ResultHolder;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
@Slf4j
public class StorageTccActionImpl implements StorageTccAction {

    @Resource
    private StorageMapper storageMapper;
    @Resource
    ResultHolder resultHolder;

    @Transactional
    @Override
    public boolean prepareDecreaseStorage(BusinessActionContext businessActionContext, String productId, BigDecimal count) {
        log.info("减少商品库存，第一阶段，锁定减少的库存量，productId=" + productId + "， count=" + count);

        Storage storage = storageMapper.selectById(productId);
        BigDecimal remain = storage.getResidue().subtract(count);
        if (remain.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("库存不足");
        }

        /*
        库存减掉count， 冻结库存增加count
         */
        storageMapper.updateFrozen(productId, count);

        //保存标识
        resultHolder.setResult(getClass(), businessActionContext.getXid(), "p");
        return true;
    }

    @Transactional
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        String productId = businessActionContext.getActionContext("productId").toString();
        BigDecimal count = new BigDecimal(businessActionContext.getActionContext("count").toString());
        log.info("减少商品库存，第二阶段提交，productId=" + productId + "， count=" + count);

        //防止重复提交
        if (resultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }

        storageMapper.updateFrozenToUsed(productId, count);
        //模拟confirm异常
//        if(1==1){
//            throw new RuntimeException("storage service confirm throws runtimeException");
//        }
        //删除标识
        resultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }

    @Transactional
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {

        String productId = businessActionContext.getActionContext("productId").toString();
        BigDecimal count = new BigDecimal(businessActionContext.getActionContext("count").toString());
        log.info("减少商品库存，第二阶段，回滚，productId=" + productId + "， count=" + count);

        //防止重复提交
        if (resultHolder.getResult(getClass(), businessActionContext.getXid()) == null) {
            return true;
        }

        storageMapper.updateFrozenToResidue(productId, count);

        //删除标识
        resultHolder.removeResult(getClass(), businessActionContext.getXid());
        return true;
    }
}
