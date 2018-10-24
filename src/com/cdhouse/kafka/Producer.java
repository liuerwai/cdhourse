package com.cdhouse.kafka;

import com.cdhouse.utils.PropertyUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@PropertySource("classpath:application.properties")
public class Producer {

    public org.apache.kafka.clients.producer.Producer producer;

    private String server = PropertyUtils.INSTANCE.getPropertiesKey("kafka.bootstrap.servers");
    private String project = PropertyUtils.INSTANCE.getPropertiesKey("kafka.consumer.groupid");

    public Producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", server);
        // acks = 0: 代表生产者不会等待broker的任何确认
        // acks = 1: 生产者会在leader服务器的副本收到消息的同一时间收到broker的确认。
        // acks = all || acks = -1: 生产者会在所有同步副本收到消息，才会收到borker确认。
        props.put("acks", "0");
        // 发送失败重试次数
        props.put("retries", 0);
        // batch.size和linger.ms的条件之一达到时， 发送消息
        props.put("batch.size", 16384);
        props.put("linger.ms", 100);
        // 缓冲消息的内存值
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // client.id 用于标识发送消息的客户端，通常用于日志和性能指标以及配额
        // max.in.flight.requests.per.connection 此配置设置客户端在单个连接上能够发送的未确认请求的最大数量，默认为5，超过此数量会造成阻塞。设置大的值可以提高吞吐量但会增加内存使用，但是需要注意的是，当设置值大于1而且发送失败时，如果启用了重试配置，有可能会改变消息的顺序。设置为1时，即使重新发送消息，也可以保证发送的顺序和写入的顺序一致。
        // max.request.size 此配置设置生产者在单个请求中能够发送的最大字节数，默认为1048576字节=1MB。例如，你可以发送单个大小为1MB的消息或者1000个大小为1KB的消息。注意，broker也有接收消息的大小限制，使用的配置是message.max.bytes=1000012字节（好奇怪的数字，约等于1MB）。
        // max.block.ms 当发送缓冲区已满或者元数据不可用时，生产者调用send()和partitionsFor()方法会被阻塞，默认阻塞时间为60000ms=1分钟。由于使用用户自定义的序列化器和分区器造成的阻塞将不会计入此时间。
        // request.timeout.ms 此配置设置客户端等待请求响应的最长时间，默认为30000ms=30秒，如果在这个时间内没有收到响应，客户端将重发请求，如果超过重试次数将抛异常。此配置应该比replica.lag.time.max.ms（broker配置，默认10秒）大，以减少由于生产者不必要的重试造成消息重复的可能性。
        producer = new KafkaProducer<>(props);
    }


    /**
     * 发送消息
     *
     * @param message
     * @param topic
     */
    public void publish(String message, String topic) {

        producer.send(new ProducerRecord(topic, project, message));
        producer.flush();
    }

    public org.apache.kafka.clients.producer.Producer getProducer() {
        return producer;
    }

    public void setProducer(org.apache.kafka.clients.producer.Producer producer) {
        this.producer = producer;
    }
}
