package com.redlips.springboot.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redlips.springboot.redis.pojo.RedisModel;
import com.redlips.springboot.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
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
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void testString() throws JsonProcessingException {
        List<RedisModel> list = new ArrayList<>();
        RedisModel model = new RedisModel();
        model.setAddress("广州");
        model.setName("qt");
        model.setRedisKey("model");
        model.setTel("2323");
        model.setMoney(100.213);
        list.add(model);
//        redisUtil.set("model", objectMapper.writeValueAsString(list));

        String jsonString = (String) redisTemplate.opsForValue().get("model");
        System.out.println("jsonString = " + jsonString);

        try {
            List<RedisModel> modelList = objectMapper.readValue(jsonString, new TypeReference<List<RedisModel>>() {
            });

            System.out.println(modelList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
//        List<RedisModel> list = new ArrayList<>();
//        RedisModel model = new RedisModel();
//        model.setAddress("广州");
//        model.setName("qt");
//        model.setRedisKey("model");
//        model.setTel("2323");
//        model.setMoney(100.213);
//        list.add(model);
//        redisUtil.set("model", list);
        List<RedisModel> modelList = (List<RedisModel>) redisUtil.get("model");
        System.out.println(modelList);
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
