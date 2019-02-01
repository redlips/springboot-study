package com.redlips.springboot.redis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiaotong
 * @create 2019-01-29 9:50
 * @description
 */
public class Test {
    public static void main(String[] args) {
        String test = "6 business days";
        Pattern pattern = Pattern.compile("\\d{1,2}");
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
