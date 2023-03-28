package cn.tedu.order.entity;

import lombok.*;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String userId;
    private String productId;
    private BigDecimal count;
    private BigDecimal money;
    private int status;

    @Transient
    private BigDecimal calcMoney;

}
