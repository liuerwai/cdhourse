package com.cdhouse.schedule;

import com.cdhouse.data.service.IDataService;
import com.cdhouse.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    @Autowired
    IDataService dataService;

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
        }
    }

    @Scheduled(cron = "0 50 17 * * *")
    @Async
    public void schedulPreSaleCrawl2() throws Exception{

        try{
            LoggerUtils.success("开始爬取交易信息");
            dataService.crawelDealInfo();
            LoggerUtils.success("爬取交易信息成功");
        } catch (Exception e){
            e.printStackTrace();
            LoggerUtils.error("爬取交易信息出错：" + e.getMessage());
        }
    }
}
