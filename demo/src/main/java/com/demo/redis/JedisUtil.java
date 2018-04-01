package com.demo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    private static String ip = "127.0.0.1";
    private static int port = 6379;
    private static String pasword = "123456";
    // 最大连接数
    private static int maxActive = 10;
    // 控制一个 pool 中最多有多少个状态为 idle（空闲）的 jedis 实例，默认试 8
    private static int maxIdle = 4;
    // 等待可用连接的最大空闲时间，单位毫秒，默认值是 -1 表示永不超时。如果等待时间超过最大空闲时间，将抛出 JedisConnectionException
    private static int maxWait = 10000;
    // 连接超时时间
    private static int timeout = 3000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean testOnBorrow = true;
    private static JedisPool jedisPool;

    // 初始化连接池
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxActive);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        jedisPool = new JedisPool(config, ip, port, timeout);

    }

    /**
     * 从 JedisPool 中获取 Redis 实例
     * @return
     */
    public synchronized static Jedis getJedis() {

        try {
            if (jedisPool != null) {
                // 从连接池中获取一个 jedis 实例
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("从 JedisPool 中获取 Redis 实例失败");
            return null;
        }
    }

    /**
     *
     * 释放资源
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }
}
