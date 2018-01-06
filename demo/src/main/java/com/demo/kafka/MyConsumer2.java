package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MyConsumer2 {

    public static void main(String[] args) {
        assignPartition0();

//        subscribeTestPartition0or1();
    }

    /**
     * 订阅 topic 名为 test 的通道
     * 订阅的是 test topic 的时候, 只会订阅 test(test 中有 2 个分区) 中的一个分区, 另一个分区由其他消费者处理
     * 处理完一个分区就 commit 一个分区的 offset
     * 这里面订阅了两个 topic
     */
    public static void subscribeTestPartition0or1() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        // 不允许自动提交偏移量
        props.put("enable.auto.commit", "false");
        // 消费者与服务器的心跳超时时间
        props.put("session.timeout.ms", "30000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
        props.put("auto.offset.reset", "earliest");
        // 指定 key、value 的序列化处理类
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));

        // 处理完一个分区（partition）的消息之后，提交对应分区的偏移量，没有处理的分区就先不提交
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition topicPartition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.printf("topic -> [%s] partition -> [%s] offset -> [%s] value -> [%s]\n", record.topic(), record.partition(), record.offset(), record.value());
                    }
                    Long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    // Collections.singletonMap() 用来得到一个不变的映射，只映射指定键为指定的值
                    consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 订阅 topic 为 test 中的 partition 为 0 的分区
     */
    public static void assignPartition0() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
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
        TopicPartition partition0  = new TopicPartition(topic, 0);
        // 通过 assign 方法订阅指定的分区
        consumer.assign(Arrays.asList(partition0));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("topic -> [%s] partition -> [%s] offset -> [%s] value -> [%s]\n", record.topic(), record.partition(), record.offset(), record.value());
            }
        }
    }
}
