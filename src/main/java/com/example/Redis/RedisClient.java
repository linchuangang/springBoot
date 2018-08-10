package com.example.Redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Scope("singleton")
//InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
public class RedisClient implements InitializingBean {

    protected static final Logger LOGGER    = LoggerFactory.getLogger(RedisClient.class);

    // jedis 实例
    private Jedis                 jedis     = null;

    private static JedisPool      jedisPool = null;

    @Autowired
    private JedisConfig           jedisConfig;

    /**
     * 关闭
     */
    public void destroy() throws Exception {

        if (jedis != null) {
            jedis.disconnect();
        }
    }

    /**
     * 进行连接
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        // jedis = JedisUtil.createJedis(jedisConfig.getHost(), jedisConfig.getPort());
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisConfig.maxActive);
        config.setMaxIdle(jedisConfig.maxIdle);
        config.setMaxWaitMillis(jedisConfig.maxWait);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, jedisConfig.host, jedisConfig.port, jedisConfig.timeout);

    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
