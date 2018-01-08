package com.demo.kafka.producer.basics;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;

/**
 * 带回调方法的消息发送者
 */
public class CallbackProducer {

    /**
     *  带回调方法的消息发送者
     */
    @Test
    public static void callbackProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("acks", "all");
        // 发送失败时 Producer 端的重试次数，默认为0
        props.put("retries", 0);
        // 批处理大小, 当同时有大量消息要向同一个分区发送时，Producer端会将消息打包后进行批量发送. 如果设置为0, 则每条消息都独立发送. 默认为16384字节
        props.put("batch.size", 16384);
        // 发送消息前等待的毫秒数
        props.put("linger.ms", 1);
        // 指定分区策略
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());
        // 消息缓冲池大小。尚未被发送的消息会保存在Producer的内存中，如果消息产生的速度大于消息发送的速度，那么缓冲池满后发送消息的请求会被阻塞, 默认 33554432字节(32M)
        props.put("buffer.memory", 33554432);
        // 指定 key 的序列化处理类,根据key和value的类型决定
        props.put("key.serializer", StringSerializer.class.getName());
        // 指定 value 的序列化处理类
        props.put("value.serializer", StringSerializer.class.getName());


        Producer<byte[], byte[]> producer = new KafkaProducer<>(props, new ByteArraySerializer(), new ByteArraySerializer());


        byte[] key = "topic test1 中的 key，是固定的".getBytes();
        byte[] value = "topic test1 中的 value，是固定的".getBytes();
//        ProducerRecord<byte[], byte[]> record = new ProducerRecord<>("test1", key, value);
        // 模拟阻塞
//        producer.send(record).get();


        for (int i = 0; i < 5; i++) {
            int finalI = i;
            producer.send(new ProducerRecord<>("test1", key, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
//                        System.out.println("Topic test1 partition 0 : " + recordMetadata.offset());
                        System.out.println("Topic test partition " + recordMetadata.partition() + " : offset : " + recordMetadata.offset() + " value : " + finalI);
                    }
                }
            });


            // Java 8 Lambda 表达式方式实现 callback
//            producer.send(new ProducerRecord<>("test", key, value), (recordMetadata, e) -> {
//                if(e != null) {
//                    e.printStackTrace();
//                } else {
//                    System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());
//                }
//            });
        }
    }
}
