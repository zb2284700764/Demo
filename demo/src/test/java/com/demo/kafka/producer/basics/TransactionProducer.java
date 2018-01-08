package com.demo.kafka.producer.basics;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Properties;

/**
 * 事务消息发送者
 */
public class TransactionProducer {

    /**
     * 事务消息发送者
     * 问题：事务 id 唯一，第一次运行成功，再次运行就不成功
     */
    @Test
    public void transactionProducer() {
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

}
