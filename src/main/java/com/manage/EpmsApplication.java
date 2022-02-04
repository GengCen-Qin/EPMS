package com.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.manage.Dao")  //包扫描
@SpringBootApplication  //标识为框架的总入口
//启动类  从这里启动整个程序
public class EpmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EpmsApplication.class, args);
    }
}
