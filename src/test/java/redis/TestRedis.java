package redis;


import com.example.DemoApplication;
import com.example.Redis.JedisConfig;
import com.example.Redis.RedisClient;

import com.example.ServletInitializar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lincg on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
//@SpringApplicationConfiguration(classes = ServletInitializar.class)// 1.4.0 前版本加载启动文件
public class TestRedis {

    @Autowired
    private JedisConfig jedisConfig;

    private Jedis jedis = null;

    private static JedisPool jedisPool = null;

    @Before
    public void connection() {
        // jedis = JedisUtil.createJedis(jedisConfig.getHost(), jedisConfig.getPort());
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisConfig.maxActive);
        config.setMaxIdle(jedisConfig.maxIdle);
        config.setMaxWaitMillis(jedisConfig.maxWait);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, jedisConfig.host, jedisConfig.port, jedisConfig.timeout);
        jedis = jedisPool.getResource();
    }

    @After
    public void disConnection(){
        if (jedis != null) {
            jedis.disconnect();
        }
    }

    @Test
    public void testString() {
        jedis.set("name", "lin");
        System.out.println(jedis.get("name"));
        jedis.append("name", "cg");
        System.out.println(jedis.get("name"));
        jedis.del("name");
        System.out.println(jedis.get("name"));
        jedis.mset("name", "lin", "age", "24");
        jedis.incr("age");
        System.out.println(jedis.get("name") + "==" + jedis.get("age"));

    }
    @Test
    public void testJedis(){
        System.out.println(jedis.get("name"));
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "lin");
        map.put("age", "24");
        map.put("address", "hangzhou");
        jedis.hmset("user", map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变
        List<String> list = jedis.hmget("user", "name", "age", "address");
        System.out.println(list);
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println(jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value
    }

    @Test
    public void testList() {
        System.out.println(jedis.hkeys("user"));
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        //先向key Java framework存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));
        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
    }

    @Test
    public void testSet() {
        jedis.sadd("user1", "lin");
        jedis.sadd("user1", "liu");
        jedis.sadd("user1", "zhang");
        //移除
        jedis.srem("user1", "zhang");
        System.out.println(jedis.smembers("user1"));//获取所有加入的value
        System.out.println(jedis.sismember("user1", "who"));//判断 who 是否是user集合的元素
        System.out.println(jedis.srandmember("user1"));
        System.out.println(jedis.scard("user1"));//返回集合的元素个数
    }

    @Test
    public void test() throws InterruptedException {
        //jedis排序
        jedis.del("a");
        jedis.rpush("a", "1");
        jedis.rpush("a", "5");
        jedis.rpush("a", "3");
        System.out.println(jedis.lrange("a", 0, -1));
        System.out.println(jedis.sort("a"));
    }
}
