package com.cdhouse.schedule;

import com.cdhouse.data.service.IDataService;
import com.cdhouse.kafka.Consumer;
import com.cdhouse.kafka.ConsumerThread;
import com.cdhouse.kafka.DealConsumerMessageImpl.CdhourseKafkaConsuerDeal;
import com.cdhouse.kafka.Producer;
import com.cdhouse.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:application.properties")
public class Schedule {

    @Autowired
    IDataService dataService;

    @Autowired
    Producer producer;

    @Scheduled(cron = "0 50 17 * * *")
    @Async
    public void schedulPreSaleCrawl() throws Exception{

        try{
            LoggerUtils.success("开始爬取预售信息");
            dataService.crawelPreSaleInfo();
            LoggerUtils.success("爬取预售信息成功");
        } catch (Exception e){
            e.printStackTrace();
            LoggerUtils.error("爬取预售信息出错：" + e.getMessage());
            producer.publish(e.getMessage(), "error");
        }
    }

    @Scheduled(cron = "0 50 17 * * *")
    @Async
    public void schedulDealInfoCrawl() throws Exception{

        try{
            LoggerUtils.success("开始爬取交易信息");
            dataService.crawelDealInfo();
            LoggerUtils.success("爬取交易信息成功");
        } catch (Exception e){
            e.printStackTrace();
            LoggerUtils.error("爬取交易信息出错：" + e.getMessage());
            producer.publish(e.getMessage(), "error");
        }
    }
}
