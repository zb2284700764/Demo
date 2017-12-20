package com.demo.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MyConsumer1 {

    public static void main(String[] args) {
        subscribeTest1Topic();

    }

    /**
     * 订阅 topic test1 通道
     */
    public static void subscribeTest1Topic() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, MyProperties.BOOTSTRAP_SERVER);
        // groupId
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
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
            for (TopicPartition topicPartition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
                for (ConsumerRecord<String, String> record : partitionRecords){
                    System.out.printf("topic -> [%s] partition -> [%s] offset -> [%s] value -> [%s]\n", record.topic(), record.partition(), record.offset(), record.value());
                }
                Long lastOffset = partitionRecords.get(partitionRecords.size() -1).offset();
                // Collections.singletonMap() 用来得到一个不变的映射，只映射指定键为指定的值
                consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(lastOffset+1)));
            }
        }
    }

}
