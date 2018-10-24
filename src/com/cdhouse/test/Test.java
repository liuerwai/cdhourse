package com.cdhouse.test;


import com.cdhouse.controller.Controller;
import com.cdhouse.dao.IDealDao;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.data.crawl.IPreSaleCrawl;
import com.cdhouse.data.service.impl.DataServiceImpl;
import com.cdhouse.kafka.CdhourseKafkaConsuerDeal;
import com.cdhouse.kafka.Consumer;
import com.cdhouse.kafka.ConsumerThread;
import com.cdhouse.kafka.Producer;
import com.cdhouse.springconfig.RootConfig;
import com.cdhouse.utils.PropertyUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
//@Transactional
public class Test {

//    @Autowired
//    KafkaUtils kafkaUtils;

    @Autowired
    WebApplicationContext webAppConfiguration;

    @Autowired
    Controller sumController;

    @Autowired
    DataServiceImpl dataService;

    @Autowired
    IDealDao dealDao;

    @Autowired
    IPreSaleDao preSaleDao;

    @Autowired
    IPreSaleCrawl preSaleCrawl;

//    @org.junit.Test
//    public void test() throws Exception{
//
//        String url = "/queryDealInfoForChartLine";
//        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webAppConfiguration).build();
//        String content = mockMvc.perform(MockMvcRequestBuilders.get(url))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn().getResponse().getContentAsString();
//        System.out.println(content);
//    }

//    @org.junit.Test
//    public void test() throws Exception{
//
//        DealPo dealPo = new DealPo();
//        dealPo.setNum(650);
//        dealPo.setTime(DateUtils.getDateFormat().parse("2018-10-17"));
//        dealPo.setType(HourseType.NEW.value);
//        dealPo.setAreaId(Area.ZHONGXIN.value);
//        dealPo.setAreaName(Area.ZHONGXIN.name);
//        dealPo.setAreaType(AreaType.MAIN_CITY.value);
//        dealDao.addDeal(dealPo);
//
//
//        dealPo.setNum(350);
//        dealPo.setType(HourseType.NEW.value);
//        dealPo.setAreaId(Area.JIAOQU.value);
//        dealPo.setAreaName(Area.JIAOQU.name);
//        dealPo.setAreaType(AreaType.DISTANT_CITY.value);
//        dealDao.addDeal(dealPo);
//
//        dealPo.setNum(220);
//        dealPo.setType(HourseType.SECOEND.value);
//        dealPo.setAreaId(Area.ZHONGXIN.value);
//        dealPo.setAreaName(Area.ZHONGXIN.name);
//        dealPo.setAreaType(AreaType.MAIN_CITY.value);
//        dealDao.addDeal(dealPo);
//
//        dealPo.setNum(100);
//        dealPo.setType(HourseType.SECOEND.value);
//        dealPo.setAreaId(Area.JIAOQU.value);
//        dealPo.setAreaName(Area.JIAOQU.name);
//        dealPo.setAreaType(AreaType.DISTANT_CITY.value);
//        dealDao.addDeal(dealPo);
//        List list = dealDao.queryDealInfo("","");
//        list.size();
//    }

//    @org.junit.Test
//    public void test()throws Exception{
//
//        dataService.crawelPreSaleInfo();
//    }


    public static void main(String[] args) throws Exception{
        Consumer consumer = new Consumer("group3");
        consumer.subcribe("liuxg3");
        consumer.addDeal(new CdhourseKafkaConsuerDeal());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ConsumerThread(consumer));
        Producer producer = new Producer();
        while (true){
            producer.publish("我是一个兵", "liuxg3");
            Thread.sleep(10000);
        }

    }


}
