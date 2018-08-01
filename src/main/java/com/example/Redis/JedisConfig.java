package com.example.Redis;

import com.example.Util.Config;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by lincg on 2017/5/16.
 */
@Service
@Scope("singleton")
public class JedisConfig {

    // 代表连接地址
    public String host      = Config.get("redis.host");

    // 代表连接port
    public int    port      = Config.getInt("redis.port");

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    public int    maxActive = Config.getInt("redis.max_active");

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    public int    maxIdle   = Config.getInt("redis.max_idle");

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    public int    maxWait   = Config.getInt("redis.max_wait");

    //默认超时时间
    public int    timeout   = Config.getInt("redis.time_out");

}