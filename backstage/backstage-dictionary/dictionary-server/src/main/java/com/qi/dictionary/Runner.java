package com.qi.dictionary;

import com.sfsctech.cloud.client.starter.EnableCloudService;
import com.sfsctech.data.mybatis.starter.EnableMybatis;
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
