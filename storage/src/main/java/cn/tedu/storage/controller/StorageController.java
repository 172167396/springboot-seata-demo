package cn.tedu.storage.controller;

import cn.tedu.storage.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    @GetMapping("/decrease")
    public String decrease(String productId, BigDecimal count) throws Exception {
        storageService.decrease(productId,count);
        return "减少商品库存成功";
    }

}
