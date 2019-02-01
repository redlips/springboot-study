package com.redlips.springboot.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author qiaotong
 * @create 2019-01-25 16:40
 * @description
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 秒
     * @return
     */
    public Boolean expire(String key, long time) {
        try {
            if (time > 0)
                return redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据Key获取过期时间
     * @param key
     * @return 秒
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断Key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 删除缓存
     * @param key 一个值或多个值
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) redisTemplate.delete(key[0]);
            else redisTemplate.delete(Arrays.asList(key));
        }
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存设置
     * @param key 键
     * @param value 值
     * @return Boolean
     */
    public Boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存设置，并设置时间
     * @param key 键
     * @param value 值
     * @param time 秒
     * @return Boolean
     */
    public Boolean set(String key, Object value, Long time) {
        try {
            if (time > 0)
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            else
                redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // ================================Map=================================

    /**
     * Hash Get
     * @param key not null
     * @param item not null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取Hash中所有的键值
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置key中多个域
     * @param key
     * @param map
     * @return
     */
    public Boolean hmset(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置Key中多个域，并设置时间
     * @param key 键
     * @param map 域（键-值）
     * @param time 秒
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向Hash表中设置数据，不存在则创建
     * @param key 键
     * @param item 项
     * @param value 项的值
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向Hash表中设置数据，并设置缓存时间
     * @param key 键
     * @param item 项
     * @param value 项的值
     * @param time 时间（秒），如果时间存在则覆盖
     * @return
     */
    public Boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除Hash表中的值
     * @param key 键
     * @param items 项，多个
     */
    public void hdel(String key, Object... items) {
        redisTemplate.opsForHash().delete(key, items);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key
     * @param item
     * @return Boolean
     */
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key
     * @param value
     * @return
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key
     * @param values
     * @return 成功的记录数
     */
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取set缓存长度
     * @param key
     * @return
     */
    public Long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的项
     * @param key
     * @param values
     * @return
     */
    public Long setRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取list缓存的内容
     * @param key
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取List缓存的长度
     * @param key
     * @return
     */
    public Long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * @param key
     * @param index index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public Long lSet(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     * @param key
     * @param values
     * @return
     */
    public Long lSet(String key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public Boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
