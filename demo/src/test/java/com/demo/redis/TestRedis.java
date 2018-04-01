package com.demo.redis;

import redis.clients.jedis.Jedis;

public class TestRedis {


    public void testString() {
        Jedis jedis = JedisUtil.getJedis();

    }

}
