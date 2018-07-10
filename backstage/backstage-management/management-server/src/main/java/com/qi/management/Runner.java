package com.qi.management;

import com.sfsctech.cloud.client.starter.EnableCloudService;
import com.sfsctech.starter.annotation.EnableMybatis;
import org.springframework.boot.SpringApplication;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudService
@EnableMybatis
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
