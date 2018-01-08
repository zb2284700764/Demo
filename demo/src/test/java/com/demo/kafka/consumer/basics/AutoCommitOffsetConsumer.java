package com.demo.kafka.consumer.basics;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * 自动提交 offset 的消费者实现类
 */
public class AutoCommitOffsetConsumer {

    @Test
    public void autoCommitOffset() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        // 允许自动提交 offset
        props.put("enable.auto.commit", "true");
        // 自动提交间隔1000毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 在 group 创建的时候如果找不到偏移量,就用最早的偏移量,此配置默认值 latest, 从当前的偏移量开始，只有在group第一次创建的时候才有作用
        props.put("auto.offset.reset", "earliest");
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
}
