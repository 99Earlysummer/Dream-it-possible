package com.dream;

import com.dream.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.time.Duration;
import java.time.Instant;

/**
 * 启动程序
 *
 * @author dream
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DreamApplication
{
    private static final Logger log = LoggerFactory.getLogger(DreamApplication.class);
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        Instant inst1 = Instant.now();
        SpringApplication.run(DreamApplication.class, args);
//        log.info(":: 启动成功!耗时:{}秒 ::", Duration.between(inst1, Instant.now()).getSeconds());
//        String pwd = SecurityUtils.encryptPassword("123456");
//        System.out.println("加密后的登录密码是："+pwd+",\r\n明文密码是:123456");

    }
}
