package com.cdhouse.controller;

import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.ResultPo;
import com.cdhouse.webservice.ISumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cdhouse.utils.LoggerUtils;

import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Controller
public class SumController {

    @Autowired
    ISumService sumService;

    @ResponseBody
    @RequestMapping("/querySum")
    public ResultPo query(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询数量汇总信息 startTime :" + startTime + "endTime" + endTime) ;
        return  ResultPo.success(sumService.querySum(startTime, endTime));
    }

    @ResponseBody
    @RequestMapping("/queryPreSaleInfo")
    public ResultPo queryPreSaleInfo(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询预售信息 startTime :" + startTime + "endTime" + endTime) ;
        List<PreSalePo> list = sumService.queryPreSaleInfo(startTime, endTime);
        return  ResultPo.success(list, list.size(), list.size());
    }
}
