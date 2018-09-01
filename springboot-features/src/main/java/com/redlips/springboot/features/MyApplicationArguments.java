package com.redlips.springboot.features;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 花落孤忆
 * @create 2018-09-01 10:05
 * @description 应用程序的参数
 */
@Component
public class MyApplicationArguments {

    @Autowired
    public MyApplicationArguments(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
    }
}
