package com.demo.kafka;

public interface MyProperties {



    String zkConnect = "localhost:2181";
    String groupId = "group1";
    String topic = "test";
    String kafkaServerURL = "localhost";
    int kafkaServerPort = 9092;
    int kafkaProducerBufferSize = 64 * 1024;
    int connectionTimeOut = 20000;
    int reconnectInterval = 10000;
    String topic2 = "topic2";
    String topic3 = "topic3";
    String clientId = "SimpleConsumerDemoClient";

}
