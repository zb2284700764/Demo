package com.demo.kafka;

public interface MyProperties {

    /**
     * kafka Server 地址
     */
    String BOOTSTRAP_SERVER = "localhost:9092";

    /**
     * 组groupId
     */
    String GROUP_ID = "test";

    /**
     * topic:test
     */
    String TOPIC_TEST = "test";
    /**
     * topic:test1
     */
    String TOPIC_TEST1 = "test1";
    /**
     * topic:test2
     */
    String TOPIC_TEST2 = "test2";

}
