package com.demo.redis;

import com.google.common.collect.Maps;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

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
        System.out.println("得到旧的值并将新的值set到对应的key：" + jedis.get(key));

        // 截取 value 中的字符串，一个汉字对应 3 个下标，一个中文标点符号占 3 个下标，下标从 0 开始
        jedis.set("hello", "张三，你好");
        System.out.println("截取 value 中的字符串，一个汉字对应 3 个下标：" + jedis.getrange("hello", 0, 11));
        jedis.set("hello1", "张三你好");
        System.out.println("截取 value 中的字符串，一个汉字对应 3 个下标：" + jedis.getrange("hello1", 0, 8));
        jedis.set("hello2", "张三,你好");
        System.out.println("截取 value 中的字符串，一个汉字对应 3 个下标：" + jedis.getrange("hello2", 0, 9));


        // 设置 key 的过期时间 秒
        jedis.setex("key", 2, "这个key会在2秒之后消失");
        System.out.println("通过 setex 设置 key 的过期时间单位（秒）：" + jedis.get("key"));

        // 当 name1 对应的 key 不存在的时候才会设置值
        jedis.setnx("name1", "name1");
        System.out.println("当 name1 对应的 key 不存在的时候才会设置值" + jedis.get("name1"));

        // 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
        jedis.setrange("name1", 4, "把数字1替换了");
        System.out.println("从指定偏移量开始替换字符串中的值：" + jedis.get("name1"));

        // key 中对应字符串的长度
        System.out.println("获取 key 对应的字符串的长度：" + jedis.strlen(key));

        // 设置多个键值对
        jedis.mset("key1", "value1", "key2", "value2", "key3", "value3");
        // 获取多个键对应的值
        List<String> list = jedis.mget("key1", "key2");
        System.out.println("得到多个key的value，返回的是 List：" + list);

        // 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
        System.out.println("同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在：" + jedis.msetnx("key1", "value1", "key4", "value4"));
        System.out.println("获取所有的 key ：" + jedis.mget("key1", "key2", "key3", "key4"));

        System.out.println("同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在：" + jedis.msetnx("key5", "value5", "key4", "value4"));
        System.out.println("获取所有的 key ：" + jedis.mget("key1", "key2", "key3", "key4", "key5"));

        // 设置 key 的过期时间 毫秒
        jedis.psetex("key11", 1000L, "这个value会在1000毫秒之后消失");
        System.out.println("通过 psetex 设置 key 的过期时间，单位（毫秒）：" + jedis.get("key11"));

        // 将 key 中储存的数字值增一
        jedis.set("int1", "1");
        System.out.println("将 key 中储存的数字值增一：" + jedis.incr("int1"));

        // 将 key 所储存的值加上给定的增量值（increment）
        System.out.println("将 key 所储存的值加上给定的增量值（increment）：" + jedis.incrBy("int1", 5));
        ;
        // 将 key 所储存的值加上给定的浮点增量值（increment）
        System.out.println("将 key 所储存的值加上给定的浮点增量值（increment）：" + jedis.incrByFloat("int1", 5.6));

        // 将 key 中储存的数字值减一，减的时候不能是 float 类型
        jedis.set("int2", "6");
        System.out.println("将 key 中储存的数字值减一：" + jedis.decr("int2"));

        // key 所储存的值减去给定的减量值（decrement）
        System.out.println("key 所储存的值减去给定的减量值（decrement）：" + jedis.decrBy("int2", 3));

        // 删除指定 key 的数据
        jedis.del(key);
        System.out.println("删除对应的 key ：" + jedis.get(key));

        // 清空整个 redis 缓存中的数据
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
        map.put("score", "88");
        // 通过 hmset 命令向 redis 中增加 map 类型的数据
        jedis.hmset("user", map);

        // 通过 hset 将新的值 set 到 map 中对应字段
        jedis.hset("user", "name", "张三");

        // 只有在字段 field 不存在时，设置哈希表字段的值
        jedis.hsetnx("user", "name", "哈哈");
        System.out.println("只有在字段 field 不存在时，设置哈希表字段的值：" + jedis.hget("user", "name"));

        // 查看哈希表 key 中，指定的字段是否存在
        System.out.println("查看哈希表 key 中，指定的字段是否存在：" + jedis.hexists("user", "age"));

        // 通过 hmget 命令获取对应 map 中的一个或多个 key 对应的 value
        System.out.println("获取对应 map 中的一个或多个 key 对应的 value ：" + jedis.hmget("user", "name", "age"));

        // 获取 map 中所有的 key-value 集合
        System.out.println("获取 map 中所有的 key-value 集合：" + jedis.hgetAll("user"));

        // 为哈希表 key 中的指定字段的整数值加上增量 increment
        jedis.hincrBy("user", "age", 3);
        System.out.println("为哈希表 key 中的指定字段的整数值加上增量 increment ：" + jedis.hget("user", "age"));

        // 为哈希表 key 中的指定字段的浮点数值加上增量 increment
        jedis.hincrByFloat("user", "score", 4.5);
        System.out.println("为哈希表 key 中的指定字段的浮点数值加上增量 increment ：" + jedis.hget("user", "score"));

        // 直接通过 key - field - value 的形式向 Hash 中存储数据
        jedis.hset("map", "name", "李四");
        // 获取 map 中指定字段的值
        System.out.println("获取 map 中 key 指定字段的 value ：" + jedis.hget("map", "name"));

        // 获取 map 中所有的 key-value 集合
        System.out.println("获取 map 中所有的 key-value ：" + jedis.hgetAll("map"));

        // 返回 key 为 user 的键中 value 的个数
        System.out.println("获取 key 为 user 的键中 value 的个数：" + jedis.hlen("user"));

        // 是否存在 key 为 user 的记录，返回 Boolean
        System.out.println("检测是否存在 key 为 user 的记录：" + jedis.exists("user"));

        // 获取 key 为 user 的 map 中的所有 key 集合
        System.out.println("获取 key 为 user 的 Hash 中的所有 key 集合：" + jedis.hkeys("user"));

        // 获取 key 为 user 的 map 中的所有 value 集合
        System.out.println("获取 key 为 user 的 Hash 中的所有 value 集合：" + jedis.hvals("user"));

        // 删除 hash 表中的一个或多个字段
        jedis.hdel("user", "name");
        System.out.println("删除 key 为 user 的 Hash 中的 key 为 name 的字段：" + jedis.hmget("user", "name"));

        // 清空整个 redis 缓存中的数据
        jedis.flushDB();
    }


    /**
     * List 列表 类型数据操作测试
     */
    public void testList() {
        Jedis jedis = JedisUtil.getJedis();
        jedis.select(15); // 切换为第16个数据库

        // 从左到右依次将 "张三", "张三", "李四", "王五" 插入列表头部 正好和 rpush 插入顺序相反
        jedis.lpush("list", "张三", "张三", "李四", "王五");
        System.out.println("从左到右依次将 \"张三\", \"张三\", \"李四\", \"王五\" 插入列表头部 正好和 rpush 插入顺序相反：" + jedis.lrange("list", 0, -1));

        // 在元素“张三”之前插入一个元素“哈哈”
        jedis.linsert("list", BinaryClient.LIST_POSITION.BEFORE, "张三", "哈哈");
        System.out.println("在元素“张三”之前插入一个元素“哈哈”：" + jedis.lrange("list", 0, -1));

        // 在元素“王五”之后插入“哈哈2”
        jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER, "王五", "哈哈2");
        System.out.println("在元素“王五”之后插入“哈哈2”：" + jedis.lrange("list", 0, -1));

        // 获取 list 中所有的数据，value 允许重复
        System.out.println("获取 list 中所有的数据，value 允许重复：" + jedis.lrange("list", 0, -1));

        // 获取 list 中指定范围的数据
        System.out.println("获取 list 中指定范围 0 - > 1 之间的数据" + jedis.lrange("list", 0, 1));

        // 通过下标获取指定位置的数据
        System.out.println("通过索引获取列表中的元素：" + jedis.lindex("list", 2));

        // 获取列表长度
        System.out.println("获取列表长度：" + jedis.llen("list"));

        // 移出并获取列表的第一个元素，并得到删除的值，如果没有值会返回 null
        jedis.lpop("list");
        System.out.println("移出并获取列表的第一个元素，并得到删除的值，如果没有值会返回 null：" + jedis.lrange("list", 0, -1));

        // 将一个或多个值插入到已存在的列表头部
        jedis.lpushx("list", "王五");
        System.out.println("将一个或多个值插入到已存在的列表头部：" + jedis.lrange("list", 0, -1));

        // 使用 lrem 移除列表中的元素
        jedis.lpush("testList", "9", "8", "7", "8", "6", "8", "8", "8", "8", "8", "5", "4", "8", "3", "8", "2", "1");
        System.out.println("testList 列表中的所有元素：" + jedis.lrange("testList", 0, -1));
        jedis.lrem("testList", 3, "8");
        System.out.println("移除列表中的元素，count > 0：从头往尾移除值为 value 的元素，移除的数量为 count 个：" + jedis.lrange("testList", 0, -1));
        ;
        jedis.lrem("testList", -5, "8");
        System.out.println("移除列表中的元素，count < 0：从尾往头移除值为 value 的元素，移除的数量为 count 个：" + jedis.lrange("testList", 0, -1));
        ;
        jedis.lrem("testList", 0, "8");
        System.out.println("移除列表中的元素，count = 0：表示移除所有值为 value 的元素：" + jedis.lrange("testList", 0, -1));

        // 通过索引设置列表元素的值，会把对应位置的值给覆盖掉
        jedis.lset("testList", 0, "0");
        System.out.println("通过索引设置列表元素的值，会把对应位置的值给覆盖掉：" + jedis.lrange("testList", 0, -1));

        // 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
        jedis.ltrim("testList", 0, 5);
        System.out.println("对一个列表进行修剪(trim)只保留指定区间内的元素：" + jedis.lrange("testList", 0, -1));

        // 移除并获取列表最后一个元素，并得到删除的值，如果没有值会返回 null
        System.out.println("移除并获取列表最后一个元素，并得到删除的值，如果没有值会返回 null：" + jedis.rpop("testList"));

        // 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
        jedis.rpoplpush("list", "newList");
        System.out.println("移除列表的最后一个元素，并将该元素添加到另一个列表并返回：" + jedis.lrange("newList", 0, -1));

        // 删除对应 key 的数据
        jedis.del("list");

        // 从左向右依次将数据插入列表尾部  正好和 lpush 插入顺序相反
        jedis.rpush("list", "张三", "李四", "王五");
        System.out.println("从左向右依次将数据插入列表尾部  正好和 lpush 插入顺序相反：" + jedis.lrange("list", 0, 1));

        // 通过索引获取列表中的元素
        System.out.println("通过索引获取列表中的元素：" + jedis.lindex("list", 2));

        // 从队列（list）的右边入队一个元素，仅队列存在时有效。当队列不存在时，不进行任何操作
        long index = jedis.rpushx("list", "赵六");
        System.out.println("从队列（list）的右边入队一个元素，仅队列存在时有效。当队列不存在时，不进行任何操作" + jedis.lrange("list", 0, index));

        // 为已存在的列表添加值，并返回对应的下标，如果不存在对应的列表将增加不成功
        long in = jedis.rpushx("list", "张三十年");
        System.out.println("为已存在的列表添加值，并返回对应的下标，如果不存在对应的列表将增加不成功："+in);
        System.out.println("为已存在的列表添加值，并返回对应的下标，如果不存在对应的列表将增加不成功："+jedis.lrange("list", 0, in));

        // 清空整个 redis 缓存中的数据
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
        System.out.println("获取集合中的成员个数："+jedis.scard("name1"));

        // 获取集合中的所有成员
        System.out.println("获取集合中的所有成员："+jedis.smembers("name2"));

        // 判断 member 元素是否是集合 key 的成员
        System.out.println("判断 member 元素是否是集合 key 的成员："+jedis.sismember("name2", "张三"));

        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println("返回 name1 集合和 name1,name3 集合的差集："+jedis.sdiff("name1", "name2", "name3"));
        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println("返回 name1 集合和 name1,name3 集合的差集："+jedis.sdiff("name2", "name1", "name3"));
        // 返回 name1 集合和 name1,name3 集合的差集
        System.out.println("返回 name1 集合和 name1,name3 集合的差集："+jedis.sdiff("name3", "name2", "name1"));

        // 将 name1 和 name2 的差集存储在 destination 集合中
        jedis.sdiffstore("destination_diff", "name1", "name2");
        System.out.println("将 name1 和 name2 的差集存储在 destination 集合中："+jedis.smembers("destination_diff"));

        // 获取传入参数的所有集合的交集
        System.out.println("获取传入参数的所有集合的交集："+jedis.sinter("name1", "name2"));
        // 没有交集返回空
        System.out.println("没有交集返回空："+jedis.sinter("name1", "name2", "name3"));

        // 获得集合的交集并将结果存储在另外一个集合中
        jedis.sinterstore("destination_inter", "name1", "name2");
        System.out.println("获得集合的交集并将结果存储在另外一个集合 destination_inter 中："+jedis.smembers("destination_inter"));

        // 将集合中的元素从 name1 集合移动到 destination_move 集合
        jedis.smove("name1", "destination_move", "李四");
        System.out.println("将集合中的元素从 name1 集合移动到 destination_move 集合："+jedis.smembers("destination_move"));

        // 随机移除集合中的一个元素并返回这个值
        System.out.println("随机移除集合中的一个元素并返回这个值："+jedis.spop("name1"));
        System.out.println("获取结婚中的所有成员：："+jedis.smembers("name1"));

        // 随机返回集合中的一个或多个元素
        System.out.println("随机返回集合中的一个或多个元素："+jedis.srandmember("name2", 2));

        // 移除集合中的一个或多个元素
        jedis.srem("name2", "张三");
        System.out.println("移除集合中的一个或多个元素：："+jedis.smembers("name2"));

        // 返回所有集合的并集
        System.out.println("返回所有集合的并集："+jedis.sunion("name1", "name2", "name3"));

        // 所有给定集合的并集存储在 destination 集合中
        jedis.sunionstore("destination_union", "name1", "name2", "name3");
        System.out.println("所有给定集合的并集存储在 destination 集合中：" + jedis.smembers("destination_union"));

        // 迭代集合中的元素,0是开始的下标
        ScanResult<String> result = jedis.sscan("name3", "0");
        System.out.println("sscan 迭代集合 ：");
        for (String str : result.getResult()) {
            System.out.print(str + ",");
        }

        // 清空整个 redis 缓存中的数据
        jedis.flushDB();
    }

    /**
     * ZSet 有序集合 类型数据操作测试
     */
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

        Map<String, Double> scoreMembers = Maps.newHashMap();
        scoreMembers.put("a", 1D);
        scoreMembers.put("b", 2D);
        scoreMembers.put("c", 3D);
        scoreMembers.put("I", 4D);
        scoreMembers.put("J", 5D);
        jedis.zadd("name2", scoreMembers);


        // 获取范围内的成员（这里获取全部）
        System.out.println("name1 集合中的元素：" + jedis.zrange("name1", 0, -1));
        System.out.println("name2 集合中的元素：" + jedis.zrange("name2", 0, -1));

        // 获得集合中成员的个数
        System.out.println("name2 集合中元素的个数：" + jedis.zcard("name2"));

        // 查询指定顺序范围内元素个数
        System.out.println("name1 集合中序号2-4之间的元素个数：" + jedis.zcount("name1", 2, 4));

        // 给元素“张三”顺序号加上 3，如果没有“张三”这个元素，那么就在顺序号 3 的位置插入“张三”
        jedis.zincrby("name1", 3, "张三");
        jedis.zincrby("name1", 3, "H");
        System.out.println("name1 集合中的元素：" + jedis.zrange("name1", 0, -1));

        // 将 name1,name2 两个集合中的交集合并并存储在一个新的 nameInter 集合中
        jedis.zinterstore("nameInter", "name1", "name2");
        System.out.println("将name1和name2集合中的交集存储在name3集合中：" + jedis.zrange("nameInter", 0, -1));

        // 返回集合中指定成员之间的成员个数
        System.out.println("获取name1集合中元素b和元素g之间的元素个数：" + jedis.zlexcount("name1", "[b", "[g"));

        // 删除集合中一个成员
        jedis.zrem("name1", "张三");
        System.out.println("删除集合中的成员张三：" + jedis.zrange("name1", 0, -1));

        // 获取集合中成员的 score
        System.out.println("获取集合中成员b的 score：" + jedis.zscore("name1", "b"));
        System.out.println("获取集合中成员c的 score：" + jedis.zscore("name1", "c"));
        System.out.println("获取集合中成员H的 score：" + jedis.zscore("name1", "张三"));

        // 获取集合中成员的索引
        System.out.println("获取集合中成员的下标：" + jedis.zrank("name1", "b"));

        // 返回指定成员区间内的成员，按成员字典正序排序, 序号必须相同
        System.out.println("获取集合中指定成员之间的所有成员按score正序排序不能有汉字：" + jedis.zrangeByLex("name1", "[b", "[f"));

        // 通过score 返回有序集合中指定区间的成员
        System.out.println("通过score 返回有序集合中指定区间的成员：" + jedis.zrangeByScore("name1", 3, 5));

        // 删除名称按字典由低到高排序成员之间所有成员
        Map<String, Double> name4Map = Maps.newHashMap();
        name4Map.put("a", 1D);
        name4Map.put("b", 1D);
        name4Map.put("c", 1D);
        name4Map.put("d", 2D);
        jedis.zadd("name4", name4Map);
        jedis.zremrangeByLex("name4", "[a", "[d");
        System.out.println("删除名称按字典由低到高排序成员之间所有成员：" + jedis.zrange("name4", 0, -1));

        // 移除有序集key中，指定排名(rank(下标))区间内的所有成员
        Map<String, Double> name5Map = Maps.newHashMap();
        name5Map.put("a", 1D);
        name5Map.put("b", 2D);
        name5Map.put("c", 3D);
        name5Map.put("d", 4D);
        name5Map.put("e", 5D);
        jedis.zadd("name5", name5Map);
        jedis.zremrangeByRank("name5", 1, 3);
        System.out.println("移除有序集key中，指定排名(rank(下标))区间内的所有成员：" + jedis.zrange("name5", 0, -1));

        // 除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员
        Map<String, Double> name6Map = Maps.newHashMap();
        name6Map.put("a", 1D);
        name6Map.put("b", 2D);
        name6Map.put("c", 3D);
        name6Map.put("d", 4D);
        name6Map.put("e", 5D);
        jedis.zadd("name6", name6Map);
        jedis.zremrangeByScore("name6", 1D, 3D);
        System.out.println("除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员：" + jedis.zrange("name6", 0, -1));

        // 返回有序集合中指定区间内的成员，通过索引倒序算，分数从高到低
        Map<String, Double> name7Map = Maps.newHashMap();
        name7Map.put("a", 1D);
        name7Map.put("b", 2D);
        name7Map.put("c", 3D);
        name7Map.put("d", 4D);
        name7Map.put("e", 5D);
        jedis.zadd("name7", name7Map);
        System.out.println("返回有序集合中指定区间内的成员，通过索引倒序算，分数从高到低：" + jedis.zrevrange("name7", 1, 4));
        ;
        // 返回有序集中指定分数区间内的成员，分数从高到低排序
        System.out.println("返回有序集中指定分数区间内的成员，分数从高到低排序：" + jedis.zrevrangeByScore("name7", 5, 1));
        ;
        // 返回有序集合中指定成员的排名
        System.out.println("返回有序集合中指定成员的排名：" + jedis.zrevrank("name7", "b"));

        // 返回有序集中，成员的分数值
        System.out.println("返回有序集中，成员的分数值：" + jedis.zscore("name7", "b"));

        // 计算一个或多个集合中的并集，并将结果存储在一个新的集合中
        jedis.zunionstore("nameUnion", "name1", "name2");
        System.out.println(jedis.zrange("nameUnion", 0, -1));

        // zscan 迭代遍历
        System.out.println("zscan 迭代遍历：");
        ScanResult<Tuple> result = jedis.zscan("nameUnion", "0");
        List<Tuple> list = result.getResult();
        for (Tuple tuple : list) {
            System.out.println(tuple.getScore() + " -> " + tuple.getElement());
        }

        // 清空整个 redis 缓存中的数据
        jedis.flushDB();
    }

}
