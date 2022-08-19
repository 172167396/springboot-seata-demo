package cn.tedu.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;

@MapperScan("cn.tedu.account.mapper")
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\proxyClass");
        SpringApplication.run(AccountApplication.class, args);
    }

}
