包名 test 为对应 test topic, test topic 里面两个分区(partition0 和 partition1)

先创建 topic
kafka-topics.sh  --zookeeper localhost:2181 --create --topic test --partitions 2 --replication-factor 1 --config max.message.bytes=64000 --config flush.messages=1
