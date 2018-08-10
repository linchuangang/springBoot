package com.example;

import com.example.Redis.RedisClient;
import com.example.Redis.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/9.
 */
public class RedisTest extends DemoApplicationTests {
    @Autowired
    private RedisUtil<Serializable> redisUtil;
    @Test
    public void set(){
        redisUtil.put("1","2");
        redisUtil.put("time","1000",100);

    }

    @Test
    public void get(){
        System.out.println(redisUtil.get("1"));
        System.out.println(redisUtil.get("time"));
    }





    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.3.1.46",6380);
        //ping通显示PONG
        System.out.println(jedis.ping());//去ping我们redis的主机所在ip和端口
    }
}
