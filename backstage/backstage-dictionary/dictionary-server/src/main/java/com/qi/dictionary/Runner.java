package com.qi.dictionary;

import com.sfsctech.cloud.net.starter.EnableCloudServer;
//import com.sfsctech.cloud.client.starter.EnableCloudClient;
import com.sfsctech.data.hikari.starter.EnableHikari;
import org.springframework.boot.SpringApplication;

/**
 * Class WebRunner
 *
 * @author 张麒 2017/7/25.
 * @version Description:
 */
@EnableCloudServer
//@EnableCloudClient
@EnableHikari
public class Runner {

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}
