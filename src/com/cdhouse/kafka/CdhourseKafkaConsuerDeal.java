package com.cdhouse.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;

public class CdhourseKafkaConsuerDeal implements IKafkaConsumerDeal {

    @Value("${kafka.consumer.key}")
    public static String key;

    @Override
    public void dealRecords(ConsumerRecord record) {

        System.out.println("=================消费消息：===============" + record.value());
    }
}
