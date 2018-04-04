

package com.demo.redis;

import com.google.common.collect.Maps;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Redis 增删改查测试
 */
public class TestRedis {


    private int databaseIndex = 15; // 都使用第 16 个数据库

    /**
     * String 类型数据操作测试
     */
    public void testString() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(databaseIndex);

        String key = "name";

        // 添加数据
        jedis.set(key, "我的名字叫张三");
        System.out.printf("set 增加数据, get 获取 value: 【%s】\n", jedis.get(key));

        // 拼接数据
        jedis.append(key, "他的名字叫李四");
        System.out.printf("append 拼接数据: 【%s】\n", jedis.get(key));

        // 得到旧的 value 并设置新的 value
        System.out.println(jedis.getSet(key, "张三，你好"));
        System.out.println(jedis.get(key));


        // 设置多个键值对
        jedis.mset("key1", "value1", "key2", "value2", "key3", "value3");
        // 获取多个键对应的值
        List<String> list = jedis.mget("key1", "key2");
        System.out.println(list);

        // key 中对应字符串的长度
        System.out.println(jedis.strlen(key));

        // 截取 value 中的字符串，一个汉字对应 3 个下标
        int startOffset = 0;
        int endOffset = 11;
        System.out.println(jedis.getrange(key, startOffset, endOffset));

        // 设置 key 的过期时间 秒
        jedis.setex("key", 2, "这个key会在2秒之后消失");
        System.out.println(jedis.get("key"));

        // 设置 key 的过期时间 毫秒
        jedis.psetex("key11", 1000, "这个value会在1000毫秒之后消失");
        System.out.println(jedis.get("key11"));

        // 删除指定 key 的数据
        jedis.del(key);
        System.out.println(jedis.get(key));

        // 删除所有的 key
        jedis.flushDB();
    }

    /**
     * Hash (Map) 类型数据操作测试
     */
    public void testHash() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(15);

        // 通过组装 map 的形式向 Hash 中存储数据
        Map<String, String> map = Maps.newHashMap();
        map.put("name", "yc");
        map.put("age", "22");
        map.put("qq", "1933108196");
        // 通过 hmset 命令向 redis 中增加 map 类型的数据
        jedis.hmset("user", map);
        // 通过 hset 将新的值 set 到 map 中对应字段
        jedis.hset("user", "name", "张三");
        // 获取 map 中所有的 key-value 集合
        System.out.println(jedis.hgetAll("user"));
        // 通过 hmget 命令获取对应 map 中的一个或多个 key 对应的 value
        System.out.println(jedis.hmget("user", "name", "age"));

        // 直接通过 key - field - value 的形式向 Hash 中存储数据
        jedis.hset("map", "name", "李四");
        // 获取 map 中指定字段的值
        System.out.println(jedis.hget("map", "name"));
        // 获取 map 中所有的 key-value 集合
        System.out.println(jedis.hgetAll("map"));

        // 返回 key 为 user 的键中 value 的个数
        System.out.println(jedis.hlen("user"));

        // 是否存在 key 为 user 的记录，返回 Boolean
        System.out.println(jedis.exists("user"));

        // 获取 key 为 user 的 map 中的所有 key 集合
        System.out.println(jedis.hkeys("user"));

        // 获取 key 为 user 的 map 中的所有 value 集合
        System.out.println(jedis.hvals("user"));

        // 删除 hash 表中的一个或多个字段
        jedis.hdel("user", "name");

        // 清空数据
        jedis.flushDB();
    }


    /**
     * List 列表 类型数据操作测试
     */
    public void testList() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(15); // 切换为第16个数据库

        // 从左到右依次将数据插入列表头部 正好和 rpush 插入顺序相反
        jedis.lpush("list", "张三", "张三", "李四", "王五");

        // 在元素“张三”之前插入一个元素“哈哈”
        jedis.linsert("list", BinaryClient.LIST_POSITION.BEFORE, "张三", "哈哈");
        // 在元素“王五”之后插入“哈哈2”
        jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER, "王五", "哈哈2");

        // 获取 list 中所有的数据，value 允许重复
        System.out.println(jedis.lrange("list", 0, -1));

        // 获取 list 中指定范围的数据
        System.out.println(jedis.lrange("list", 0, 1));

        // 通过下标获取指定位置的数据
        System.out.println(jedis.lindex("list", 2));

        jedis.del("list");

        // 从左向右依次将数据插入列表尾部  正好和 lpush 插入顺序相反
        jedis.rpush("list", "张三", "李四", "王五");
        System.out.println(jedis.lrange("list", 0, 1));
        System.out.println(jedis.lindex("list", 2));


        // 从队列（list）的右边入队一个元素，仅队列存在时有效。当队列不存在时，不进行任何操作
        long index = jedis.rpushx("list", "赵六");
        System.out.println(index);
        System.out.println(jedis.lrange("list", 0, index));

        // 从队列（list）的头部删除一个元素
        System.out.println(jedis.lpop("list"));
        System.out.println(jedis.lpop("list"));

        // 从队列（list）的尾部删除一个元素
        System.out.println(jedis.rpop("list"));
        System.out.println(jedis.rpop("list"));
        System.out.println(jedis.rpop("list"));

        long in = jedis.rpushx("list", "张三十年");
        System.out.println(in);
        System.out.println(jedis.lrange("list", 0, in));

        // 清空数据
        jedis.flushDB();
    }

    /**
     * Zet 集合 类型数据操作测试
     */
    public void testSet() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(15);

        // 向 set 中增加一个或多个数据，不允许有重复数据
        jedis.sadd("name1", "张三", "李四", "test1");
        jedis.sadd("name2", "张三", "王五", "test2");
        jedis.sadd("name3", "赵六", "王五", "test3");

        // 获取集合中的成员数
        System.out.println(jedis.scard("name1"));

        // 获取集合中的所有成员
        System.out.println(jedis.smembers("name2"));

        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println(jedis.sdiff("name1", "name2", "name3"));
        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println(jedis.sdiff("name2", "name1", "name3"));
        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println(jedis.sdiff("name3", "name2", "name1"));

        // 将 name1 和 name2 的差集存储在 destination 集合中
        jedis.sdiffstore("destination_diff", "name1", "name2");
        System.out.println(jedis.smembers("destination_diff"));

        // 获取传入参数的所有集合的交集
        System.out.println(jedis.sinter("name1", "name2"));
        // 没有交集返回空
        System.out.println(jedis.sinter("name1", "name2", "name3"));

        // 获得集合的交集并将结果存储在另外一个集合中
        jedis.sinterstore("destination_inter", "name1", "name2");
        System.out.println(jedis.smembers("destination_inter"));

        // 将集合中的元素从 name1 集合移动到 destination_move 集合
        jedis.smove("name1", "destination_move", "李四");
        System.out.println(jedis.smembers("destination_move"));

        // 随机移除集合中的一个元素并返回这个值
        System.out.println(jedis.spop("name1"));
        System.out.println(jedis.smembers("name1"));

        // 随机返回集合中的一个或多个元素
        System.out.println(jedis.srandmember("name2", 2));

        // 移除集合中的一个或多个元素
        jedis.srem("name2", "张三");
        System.out.println(jedis.smembers("name2"));

        // 返回所有集合的并集
        System.out.println(jedis.sunion("name1", "name2", "name3"));

        // 清空数据
        jedis.flushDB();
    }

    /**
     * ZSet 有序集合 类型数据操作测试
     */
    @Test
    public void testZSet() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(15);

        // 向有序集合中添加元素，添加的时候指定顺序
        jedis.zadd("name1", 1, "a");
        jedis.zadd("name1", 2, "b");
        jedis.zadd("name1", 3, "c");
        jedis.zadd("name1", 4, "d");
        jedis.zadd("name1", 5, "e");
        jedis.zadd("name1", 6, "f");
        jedis.zadd("name1", 7, "g");

        Map<String,Double> scoreMembers = Maps.newHashMap();
        scoreMembers.put("a", 1D);
        scoreMembers.put("b", 2D);
        scoreMembers.put("c", 3D);
        jedis.zadd("name2", scoreMembers);


        // 获取范围内的成员（这里获取全部）
        System.out.println("name1 集合中的元素："+jedis.zrange("name1", 0, -1));
        System.out.println("name2 集合中的元素："+jedis.zrange("name2", 0, -1));

        // 获得集合中成员的个数
        System.out.println("name2 集合中元素的个数："+jedis.zcard("name2"));

        // 查询指定顺序范围内元素个数
        System.out.println("name1 集合中序号2-4之间的元素个数："+jedis.zcount("name1", 2, 4));

        // 给元素“张三”顺序号加上 3，如果没有“张三”这个元素，那么就在顺序号 3 的位置插入“张三”
        jedis.zincrby("name1", 3, "张三");
        System.out.println("name1 集合中的元素："+jedis.zrange("name1", 0, -1));



        // 清空数据
        jedis.flushDB();
    }

}
