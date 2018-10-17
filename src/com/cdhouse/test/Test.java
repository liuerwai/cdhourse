package com.cdhouse.test;


import com.cdhouse.controller.Controller;
import com.cdhouse.dao.IDealDao;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.data.service.impl.DataServiceImpl;
import com.cdhouse.po.DealPo;
import com.cdhouse.springconfig.RootConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
//@Transactional
public class Test {

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

    @org.junit.Test
    public void test() throws Exception{

        String url = "/queryDealInfoForChartLine";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webAppConfiguration).build();
        String content = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

//    @org.junit.Test
//    public void test() throws Exception{
//        dataService.crawelDealInfo();
//        dataService.crawelPreSaleInfo();
//        dealDao.queryDealInfo("2017-07-01", "2018-12-12");
//        dealDao.querySum("2017-07-01", "2018-12-12");
//        preSaleDao.queryPreSale("2017-07-01", "2018-12-12");
//        preSaleDao.querySum("2017-07-01", "2018-12-12");
//    }

}
