package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.tcc.OrderTccAction;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@ConditionalOnProperty(prefix = "seata", value = "mode", havingValue = "TCC")
@Service
public class OrderTccServiceImpl implements OrderService {
    @Resource
    private AccountClient accountClient;
    @Resource
    private StorageClient storageClient;

    @Resource
    private OrderTccAction orderTccAction;

    //    @GlobalTransactional
    @Override
    public void create(Order order) {
        // 从全局唯一id发号器获得id
//        Long orderId = easyIdGeneratorClient.nextId("order_business");
        String orderId = UUID.randomUUID().toString().replace("-", "");
        order.setId(orderId);

        // orderMapper.create(order);

        // 这里修改成调用 TCC 第一节端方法
        orderTccAction.prepareCreateOrder(
                null,
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getCount(),
                order.getMoney());


        // 修改库存
        storageClient.decrease(order.getProductId(), order.getCount());

        // 修改账户余额
        accountClient.decrease(order.getUserId(), order.getMoney());

    }
}