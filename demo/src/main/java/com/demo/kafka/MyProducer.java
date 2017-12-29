package com.demo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 消息产生者
 */
public class MyProducer {

    public static void main(String[] args) {

        // 用 test topic
        basicProducer();

        // 用 test1 topic
//        try {
//            callbackProducer();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    /**
     *  带回调方法的消息发送者
     * @throws ExecutionException
     */
    public static void callbackProducer() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("acks", "all");
        // 发送失败时 Producer 端的重试次数，默认为0
        props.put("retries", 0);
        // 批处理大小, 当同时有大量消息要向同一个分区发送时，Producer端会将消息打包后进行批量发送. 如果设置为0, 则每条消息都独立发送. 默认为16384字节
        props.put("batch.size", 16384);
        // 发送消息前等待的毫秒数
        props.put("linger.ms", 1);
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
            producer.send(new ProducerRecord<>("test1", key, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        System.out.println("Topic test1 partition 0 : " + recordMetadata.offset());
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

    /**
     * 事务消息发送者
     * 问题：事务 id 唯一，第一次运行成功，再次运行就不成功
     */
    public static void transactionProducer() {
        Properties props = new Properties();
        // 主机地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 事务唯一ID
        props.put("transactional.id", "my-transactional-id");
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        // 初始化事务
        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 0; i < 5; i++) {
                producer.send(new ProducerRecord<>("test", "key->" + i, "value->" + i));
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            producer.close();
            e.printStackTrace();
        } catch (KafkaException e) {
            // 终止事务
            producer.abortTransaction();
        }
        producer.close();
    }

    /**
     * 基础的消息发送者
     */
    public static void basicProducer() {
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
            producer.send(new ProducerRecord<>("test", "topic test partition 0 中的 key->" + i, " topic test 中的 value->" + i));
        }
        producer.close();
    }
}
