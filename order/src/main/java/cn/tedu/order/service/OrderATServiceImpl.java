package cn.tedu.order.service;

import cn.tedu.order.entity.Order;
import cn.tedu.order.feign.AccountClient;
import cn.tedu.order.feign.StorageClient;
import cn.tedu.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
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

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        if (!StringUtils.hasLength(order.getId())) {
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setId(orderId);
        }
        orderMapper.create(order);
//        orderMapper.deleteById(order.getId());
        String productId = order.getProductId();
        List<String> strings = Arrays.asList(productId.split(","));
        strings.forEach(pid -> {
            // 修改库存
            storageClient.decrease(pid, order.getCount());
        });
        // 修改账户余额
        accountClient.decrease(order.getUserId(), order.getMoney());
        if (1 == 1) {
            throw new RuntimeException("模拟异常");
        }
    }
}