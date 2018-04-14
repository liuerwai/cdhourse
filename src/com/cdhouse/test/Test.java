package com.cdhouse.test;


import com.cdhouse.controller.SumController;
import com.cdhouse.springconfig.RootConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import  org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
@Transactional
public class Test {

    @Autowired
    SumController sumController;

    @org.junit.Test
    public void test() throws Exception{

        String url = "/querySum";
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sumController).build();
        String content = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

}
