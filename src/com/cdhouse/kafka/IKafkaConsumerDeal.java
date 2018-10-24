package com.cdhouse.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaConsumerDeal {

    void dealRecords(ConsumerRecord records);
}
