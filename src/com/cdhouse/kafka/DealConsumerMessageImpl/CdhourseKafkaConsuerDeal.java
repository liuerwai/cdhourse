package com.cdhouse.kafka.DealConsumerMessageImpl;

import com.cdhouse.kafka.DealConsumerMessageDao.IKafkaConsumerDeal;
import com.cdhouse.utils.MailUtils;
import com.cdhouse.utils.PropertyUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class CdhourseKafkaConsuerDeal implements IKafkaConsumerDeal {

    public static String project = PropertyUtils.INSTANCE.getPropertiesKey("kafka.project");

    @Override
    public void dealRecords(ConsumerRecord record) {
        if (record.key().equals(project)) {
            MailUtils.sendMail("成都房产报错啦", record.value().toString(), "liuxg@channelsoft.com");
        }
    }
}
