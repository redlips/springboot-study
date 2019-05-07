package com.redlips.springboot.redis;

import com.redlips.springboot.redis.pojo.RedisModel;
import com.redlips.springboot.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author qiaotong
 * @create 2019-01-25 15:14
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void test() {
        List<RedisModel> list = new ArrayList<>();
        RedisModel model = new RedisModel();
        model.setAddress("广州");
        model.setName("qt");
        model.setRedisKey("model");
        model.setTel("2323");
        list.add(model);
        redisUtil.set("model", list);
    }

    @Test
    public void test2() {
        List<RedisModel> model = (List<RedisModel>) redisUtil.get("model");
        System.out.println(model);
    }

    @Test
    public void test3() {
        String key = "班级1";
//        for (int i = -1; i < 100; i++) {
//            RedisModel redisModel = new RedisModel();
//            redisModel.setScore(i);
//            redisTemplate.opsForZSet().add(key, redisModel, redisModel.getScore());
//        }
        Set<Object> range = redisTemplate.opsForZSet().range(key, 1, 2);
        System.out.println(range);
    }

}
