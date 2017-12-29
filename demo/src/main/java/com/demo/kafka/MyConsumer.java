package com.demo.kafka;

import com.google.common.collect.Lists;
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

/**
 * 消息消费者
 */
public class MyConsumer {

    public static void main(String[] args) {
        // 轮询每个 topic ，轮询完一个就提交这个 topic 的 offset
        subscribeTestTopic();


        // 手动提交 offset
//        manualCommitOffset();


        // 自动提交 offset
//        autoCommitOffset();
    }


    /**
     * 自动提交 offset
     */
    public static void autoCommitOffset() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        // 允许自动提交 offset
        props.put("enable.auto.commit", "true");
        // 自动提交间隔1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
//        props.put("auto.offset.reset", "earliest");
        // 指定 key 的序列化处理类,根据key和value的类型决定
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

    /**
     * 手动提交 offset
     */
    public static void manualCommitOffset() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        // 不允许自动提交 offset
        props.put("enable.auto.commit", "false");
        // 消费者与服务器的心跳超时时间
        props.put("session.timeout.ms", "30000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
//        props.put("auto.offset.reset", "earliest");
        // 指定 key 的序列化处理类,根据key和value的类型决定
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("test"));

        int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = Lists.newArrayList();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                buffer.add(record);
            }
            if (buffer.size() > minBatchSize) {
                System.out.println("Insert into DB");
                consumer.commitSync();
                buffer.clear();
            }
        }
    }


    /**
     * 订阅 topic 名为 test 的通道
     * 订阅的是 test topic 的时候, 只会订阅 test(test 中有 2 个分区) 中的一个分区, 另一个分区由其他消费者处理
     * 处理完一个分区就 commit 一个分区的 offset
     * 这里面订阅了两个 topic
     */
    public static void subscribeTestTopic() {

        Properties props = new Properties();
        props.put("bootstrap.servers","localhost:9092");
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
                for (TopicPartition topicPartition : records.partitions()){
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
                    for (ConsumerRecord<String, String> record : partitionRecords){
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

}
