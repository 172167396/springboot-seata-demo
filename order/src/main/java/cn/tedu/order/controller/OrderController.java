package cn.tedu.order.controller;

import cn.tedu.order.entity.Order;
import cn.tedu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    @Resource
    OrderService orderService;

    @PostMapping("/create")
    public String create(@RequestBody Order order) {
        log.info("创建订单");
        orderService.create(order);
        return "创建订单成功";
    }
}
