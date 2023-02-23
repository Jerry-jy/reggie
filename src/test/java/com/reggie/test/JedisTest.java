package com.reggie.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * ClassName: JedisTest
 * Package: com.reggie.test
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-23 9:36
 * @Version 1.0
 */
public class JedisTest {
    @Test
    public void testRedis(){
        // 1、获取连接
        Jedis jedis = new Jedis("192.168.10.129", 6379);

        // 2、执行具体操作
        //添加一个[key, value]
        jedis.set("username", "张三");

        // 获取value
        String username = jedis.get("username");
        System.out.println("username = " + username);

        //删除
        jedis.del("username");
        System.out.println("username = " + username);

        // 存hashset
        jedis.hset("myhashset", "addr","beijing");
        String hget = jedis.hget("myhashset", "addr");
        System.out.println("hget = " + hget);

        // 3、关闭连接
        jedis.close();
    }
}
