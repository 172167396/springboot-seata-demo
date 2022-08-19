package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "AT")
@Service
public class OrderATServiceImpl implements OrderService {
    @Resource
    private AccountClient accountClient;
    @Resource
    private StorageClient storageClient;
    @Resource
    OrderMapper orderMapper;

    @GlobalTransactional
    @Override
    public void create(Order order) {
        String orderId = UUID.randomUUID().toString().replace("-", "");
        order.setId(orderId);
        orderMapper.create(order);
        // 修改库存
        storageClient.decrease(order.getProductId(), order.getCount());
        // 修改账户余额
        accountClient.decrease(order.getUserId(), order.getMoney());
        if(1==1){
            throw new RuntimeException("模拟异常");
        }
    }
}