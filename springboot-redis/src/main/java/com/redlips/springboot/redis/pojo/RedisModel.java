package com.redlips.springboot.redis.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qiaotong
 * @create 2019-01-28 11:47
 * @description
 */
@Data
public class RedisModel implements Serializable {
    private String redisKey;    //redis中的key
    private String name;    //姓名
    private String tel; //电话
    private String address; //住址
    private int score;
}
