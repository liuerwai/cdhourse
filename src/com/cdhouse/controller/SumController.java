package com.cdhouse.controller;

import com.cdhouse.po.ResultPo;
import com.cdhouse.po.Test;
import com.cdhouse.service.ISumService;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cdhouse.utils.LoggerUtils;

import java.io.IOException;
import java.util.ArrayList;
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
    @RequestMapping("/queryDetails")
    public ResultPo queryDetails(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询详细信息 startTime :" + startTime + "endTime" + endTime) ;
        Test  test= new Test();
        test.setSomeAttribute("aa");
        test.setSomeOtherAttribute("bb");
        List<Test> listTest = new ArrayList<>();
        listTest.add(test);
        listTest.add(test);
        return  ResultPo.success(listTest);
    }
}
