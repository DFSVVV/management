package com.lon.uesrmanagerment;

import com.lon.uesrmanagerment.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.lon.uesrmanagerment.mapper")
public class UesrmanagermentApplication {

    public static void main(String[] args) {
        SpringApplication.run(UesrmanagermentApplication.class, args);
    }

}
