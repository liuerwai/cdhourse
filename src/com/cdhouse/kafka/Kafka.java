package com.cdhouse.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Component
public class Kafka {

    public void publish(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.132.10.206:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        Future<RecordMetadata> xx = null;
        for (int i = 0; i < 100; i++) {
            xx =  producer.send(new ProducerRecord<>("liuxg3", "sfsfsfsfsf","ssssffff"));
        }
        System.out.println(xx.isDone());
        producer.close();
    }

    public void consumer(){
        Properties props = new Properties();
        props.put("auto.offset.reset", "earliest");
        props.put("bootstrap.servers", "10.132.10.206:9092");
        props.put("group.id", "group2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton("liuxg3"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                record.partition();
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }

    }

    public static void main(String[] args){

        Kafka kafka = new Kafka();
        kafka.consumer();
    }

}
