package com.cdhouse.kafka;

import com.cdhouse.kafka.DealConsumerMessageDao.IKafkaConsumerDeal;
import com.cdhouse.kafka.DealConsumerMessageImpl.CdhourseKafkaConsuerDeal;
import com.cdhouse.utils.PropertyUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Scope("prototype")
@PropertySource("classpath:application.properties")
@Component
public class Consumer {

    @Value("${kafka.bootstrap.servers}")
    private String servers = PropertyUtils.INSTANCE.getPropertiesKey("kafka.bootstrap.servers");

    public Set<String> cusumerToplicSet = new HashSet<>();

    public List<IKafkaConsumerDeal> deals = new ArrayList<>();


    public final KafkaConsumer<String, String> consumer;

    public Consumer(String groupId) {

        Properties props = new Properties();
        // 该配置设置消费者在开始读取一个没有提交偏移量或该偏移量为非法的分区时如何重置该偏移量 earliest 从头开始  latest 从结尾开始
        props.put("auto.offset.reset", "latest");
        //
        props.put("bootstrap.servers", servers);
        props.put("group.id", groupId);
        // 消费者向Kafka发送一条包含分区偏移量的消息到一个特别的topic：_consumer_offsets用于更新分区偏移量，这样的操作称为提交commit
        // 同步提交 commitSync()
        // 异步提交 commitAsync();
        // 提交指定的offset commitAsync(Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback) commitSync(Map<TopicPartition, OffsetAndMetadata> offsets)
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "10000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // fetch.min.bytes 该配置设置broker返回数据的最小字节数，默认是1字节。如果一个broker接收到来自消费者的读取消息请求，但新消息的大小小于该配置的值，那么这个broker会等待更多的消息直到接收的新消息大小等于该配置的值。这样的机制会减少消费者和broker的负载，因为可以减少处理来回消息的频率。所以，在broker接收到的消息不多时，可以把该配置的值设大一点，这样可以减少消费者使用的CPU资源，或者在配置了大量消费者时减少broker的负载。此配置和下面的fetch.max.wait.ms一起使用
        // fetch.max.wait.ms 该配置设置broker最长的等待时间，默认是500ms。如果一个broker接收到的新消息大小小于fetch.min.bytes的值，那么这个broker会一直等待直到时间超过配置的500ms。如果想减少延时，可以把该配置的值设小一点。此配置和上面的fetch.min.bytes是一起使用的，例如fetch.max.wait.ms=100，fetch.min.bytes=1048576(1MB)，当broker数据达到1MB或者等待了100ms时，broker都会把数据发送给消费者
        // max.partition.fetch.bytes 该配置设置每个分区返回数据的最大字节数，默认是1048576字节=1MB。如果一个topic有20个分区，配置了5个消费者，那么每个消费者则需要4MB内存，用于存放KafkaConsumer.poll()返回的ConsumerRecord对象。实际上，如果消费者组内有消费者发生故障，由于分区再均衡，每个消费者则需要处理更多的分区消息，那么建议分配更多的内存。该配置值必须大于broker能够接收消息的最大大小message.max.bytes=1000012(0.96MB)，否则消费者将会hang住。 设置该配置的另一个重要考虑因素是消费者处理数据所花费的时间。消费者必须频繁地调用poll()方法以避免session超时导致分区再均衡，如果单次poll()返回的数据非常大，那么消费者需要更长的时间去处理，这意味着它不能及时调用下一次的poll()方法，从而导致session的超时。这个时候可以减小该配置的值或者增加session的超时时间。
        // session.timeout.ms 该配置设置session超时时间，默认是10000=10秒。消费者会周期性地发送心跳给broker，间隔为heartbeat.interval.ms=3000=3秒，如果超过session.timeout.ms配置的时间broker都没有收到心跳，那么对应的消费者就会被移除，然后触发分区再均衡。
        consumer = new KafkaConsumer<>(props);
    }


    /**
     * 消费消息
     */
    public void receiveDealMessage() {

        synchronized (consumer) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                synchronized (deals) {
                    for (IKafkaConsumerDeal deal : deals) {
                        deal.dealRecords(record);
                    }
                }
            }
        }
    }

    /**
     * 订阅
     *
     * @param topic
     */
    public void subcribe(String topic) {

        synchronized (consumer) {
            if (!cusumerToplicSet.contains(topic)) {
                consumer.subscribe(Collections.singleton(topic));
            }
        }
    }

    /**
     * 增加处理
     * @param deal
     */
    public void addDeal(CdhourseKafkaConsuerDeal deal){

        synchronized (deals) {
            deals.add(deal);
        }
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public Set<String> getCusumerToplicSet() {
        return cusumerToplicSet;
    }

    public void setCusumerToplicSet(Set<String> cusumerToplicSet) {
        this.cusumerToplicSet = cusumerToplicSet;
    }

    public List<IKafkaConsumerDeal> getDeals() {
        return deals;
    }

    public void setDeals(List<IKafkaConsumerDeal> deals) {
        this.deals = deals;
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }
}
