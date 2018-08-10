package com.example.Redis;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/9.
 */
@Service()
@Scope("singleton")
public class RedisUtil<T extends Serializable> {

    @Autowired
    RedisClient redisClient;

    private int defaultExpiredSeconds = 0;

    private Jedis init(String key, T val) {
        Jedis jds = redisClient.getJedis();
        return jds;
    }

    private Jedis init(String key) {
        return init(key, null);
    }

    /**
     * 设置会失效的key:value
     * @param key
     * @param val
     * @param expiredTime 单位 秒
     */
    public void put(String key, T val, int expiredTime) {
        Jedis jds = init(key, val);
        try {
            jds.setex(key.getBytes(), expiredTime, SerializationUtils.serialize(val));
        } catch (Exception e) {
            throw new RedisCacheException("Put cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * 设置不会失效的key:value
     * @param key
     * @param val

     */
    public void put(String key, T val) throws RedisCacheException {
        Jedis jds = init(key, val);
        try {
            if (defaultExpiredSeconds > 0) {
                jds.setex(key.getBytes(), defaultExpiredSeconds, SerializationUtils.serialize(val));
            } else {
                jds.set(key.getBytes(), SerializationUtils.serialize(val));
            }
        } catch (Exception e) {
            throw new RedisCacheException("Put cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     * @throws RedisCacheException
     */
    public T get(String key) throws RedisCacheException {
        Jedis jds = init(key);
        try {
            byte[] ret = jds.get(key.getBytes());
            if (ret == null) {
                return null;
            }

            return SerializationUtils.<T> deserialize(ret);

        } catch (Exception e) {
            throw new RedisCacheException("get cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * 删除key
     * @param key
     * @throws RedisCacheException
     */
    public void delete(String key) throws RedisCacheException {
        Jedis jds = init(key);
        try {
            jds.del(key);
        } catch (Exception e) {
            throw new RedisCacheException("delete cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * 使key失效
     * @param key
     * @param expiredTime 单位：秒
     * @throws RedisCacheException
     */
    public void expire(String key, int expiredTime) throws RedisCacheException {
        Jedis jds = init(key);
        try {
            jds.expire(key, expiredTime);
        } catch (Exception e) {
            throw new RedisCacheException("expire cache exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }

    /**
     * 获取剩余失效时间
     * @param key
     * @throws RedisCacheException
     */
    public Long ttl(String key) throws RedisCacheException {
        Jedis jds = init(key);
        Long count = 0L;
        try {
            count = jds.ttl(key);
            return count;
        } catch (Exception e) {
            throw new RedisCacheException("ttl exception.", e);
        } finally {
            if (jds != null) {
                jds.close();
            }
        }
    }
}

