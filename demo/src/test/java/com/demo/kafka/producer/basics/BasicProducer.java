package com.demo.kafka.producer.basics;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;

/**
 * 基础的消费发送者
 */
public class BasicProducer {

    /**
     * 基础的消息发送者
     */
    @Test
    public void basicProducer() {
        Properties props = new Properties();
        // 集群地址
        props.put("bootstrap.servers", "localhost:9092");
        /*
         * broker 消息确认的模式，有三种
         * 0：不进行消息接收确认，即Client端发送完成后不会等待Broker的确认
         * 1：由Leader确认，Leader接收到消息后会立即返回确认信息
         * all：集群完整确认，Leader会等待所有in-sync的follower节点都确认收到消息后，再返回确认信息
         */
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
        // 分区策略, 消息发送到哪个分区
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());

        // 指定 key 的序列化处理类,根据key和value的类型决定
        props.put("key.serializer", StringSerializer.class.getName());
        // 指定 value 的序列化处理类
        props.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
//            producer.send(new ProducerRecord<>("test", "topic test partition 0 中的 key->" + i, " topic test 中的 value->" + i));

            int finalI = i;
            producer.send(new ProducerRecord<>("test1", "topic test partition 0 中的 key->" + i, " topic test 中的 value->" + i), (recordMetadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.println("Topic test partition " + recordMetadata.partition() + " : offset : " + recordMetadata.offset() + " value : " + finalI);
                }
            });
        }
        producer.close();
    }
}
