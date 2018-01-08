package com.demo.kafka.consumer.test;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 自动订阅相同topic下的一个分区
 * 这个类订阅 test topic 的一个分区
 * test 中有两个分区(0或1)
 * 当前类启多个实例就是多个消费者
 */
public class AutoSubscribeTopicPartition {

    /**
     * 订阅 topic 名为 test 的通道
     * 如果订阅了多个 topic，那么处理完一个分区就 commit 一个分区的 offset
     * 1、启动一个实例的时候--该实例订阅了 test topic 的所有分区
     * 2、启动两个实例的时候--两个实例分别订阅 test topic 的一个分区
     */
    @Test
    public void subscribeTestPartition0or1() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // groupId
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        // 不允许自动提交偏移量
        props.put("enable.auto.commit", "false");
        // 消费者与服务器的心跳超时时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 指定 key、value 的序列化处理类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        // 订阅名称为 test1 的 topic
        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic -> [%s] partition -> [%s] offset -> [%s] value -> [%s]\n", record.topic(), record.partition(), record.offset(), record.value());
            }
        }
    }
}
