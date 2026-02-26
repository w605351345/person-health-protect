package com.personhealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 家庭健康档案系统 - 主应用类
 *
 * @author PersonHealth Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@MapperScan("com.personhealth.mapper")
public class PersonHealthProtectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonHealthProtectApplication.class, args);
        System.out.println("""
            =========================================
              家庭健康档案系统启动成功！
              API 文档: http://localhost:8080/api/swagger-ui.html
            =========================================
            """);
    }
}
