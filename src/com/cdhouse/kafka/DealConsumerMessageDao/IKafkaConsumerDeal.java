package com.cdhouse.kafka.DealConsumerMessageDao;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaConsumerDeal {

    void dealRecords(ConsumerRecord records);
}
