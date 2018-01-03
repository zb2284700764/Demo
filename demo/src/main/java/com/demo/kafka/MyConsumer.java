package com.demo.kafka;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 消息消费者
 */
public class MyConsumer {

    public static void main(String[] args) {


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



}
