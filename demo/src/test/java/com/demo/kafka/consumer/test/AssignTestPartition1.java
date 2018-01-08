package com.demo.kafka.consumer.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * 订阅同一个 topic 下的不同 partition
 */
public class AssignTestPartition1 {

    /**
     * 订阅 topic 为 test1 中的 partition 为 1 的分区
     */
    @Test
    public void assignPartition1() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group2");
        // 不允许自动提交偏移量
        props.put("enable.auto.commit", "true");
        // 消费者与服务器的心跳超时时间
        props.put("session.timeout.ms", "30000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
        props.put("auto.offset.reset", "earliest");
        // 指定 key、value 的序列化处理类
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        String topic = "test";
        // 订阅 test topic 中的0分区
        TopicPartition partition1 = new TopicPartition(topic, 1);
        // 通过 assign 方法订阅指定的分区
        consumer.assign(Arrays.asList(partition1));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic -> [%s] partition -> [%s] offset -> [%s] value -> [%s]\n", record.topic(), record.partition(), record.offset(), record.value());
            }
        }
    }
}
