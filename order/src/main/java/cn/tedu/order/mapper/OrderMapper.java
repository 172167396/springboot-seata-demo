package cn.tedu.order.mapper;

import cn.tedu.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    void create(Order order);
    void updateStatus(@Param("orderId") String orderId, @Param("status") Integer status);

}
