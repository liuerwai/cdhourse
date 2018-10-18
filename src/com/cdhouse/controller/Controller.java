package com.cdhouse.controller;

import com.cdhouse.contans.AreaType;
import com.cdhouse.contans.HourseType;
import com.cdhouse.po.DealPo;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.ResultPo;
import com.cdhouse.utils.LoggerUtils;
import com.cdhouse.webservice.IService;
import org.jsoup.select.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    IService sumService;

    @ResponseBody
    @RequestMapping("/querySum")
    public ResultPo query(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询数量汇总信息 startTime :" + startTime + "endTime" + endTime);
        return ResultPo.success(sumService.querySum(startTime, endTime));
    }

    @ResponseBody
    @RequestMapping("/queryPreSaleInfo")
    public ResultPo queryPreSaleInfo(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询预售信息 startTime :" + startTime + "endTime" + endTime);
        List<PreSalePo> list = sumService.queryPreSaleInfo(startTime, endTime);
        return ResultPo.success(list, list.size(), list.size());
    }

    @ResponseBody
    @RequestMapping("/queryDealInfo")
    public ResultPo queryDealInfo(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询交易信息 startTime :" + startTime + "endTime" + endTime);
        List<DealPo> list = sumService.queryDealInfo(startTime, endTime);
        return ResultPo.success(list, list.size(), list.size());
    }

    @ResponseBody
    @RequestMapping("/queryDealInfoForChartLine")
    public ResultPo queryDealInfoForChartLine(String startTime, String endTime) throws IOException {

        LoggerUtils.info("查询交易信息 startTime :" + startTime + "endTime" + endTime);
        List<DealPo> dealList = sumService.queryDealInfo(startTime, endTime);
        Map<String, List> result = new HashMap<String, List>();
        result.put("X", new ArrayList());
        result.put("newHourse", new ArrayList());
        result.put("secondHourse", new ArrayList());
        result.put("newHourseMainCity", new ArrayList());
        result.put("secondHourseMainCity", new ArrayList());
        result.put("newHourseDistantCity", new ArrayList());
        result.put("secondHourseDistantCity", new ArrayList());

        Map map1 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.NEW.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        Map map2 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.SECOEND.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        Map map3 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.NEW.value))
                .filter(dealPo -> dealPo.getAreaType().equals(AreaType.MAIN_CITY.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        Map map4 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.NEW.value))
                .filter(dealPo -> dealPo.getAreaType().equals(AreaType.DISTANT_CITY.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        Map map5 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.SECOEND.value))
                .filter(dealPo -> dealPo.getAreaType().equals(AreaType.MAIN_CITY.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        Map map6 = dealList.stream().filter(dealPo -> dealPo.getType().equals(HourseType.SECOEND.value))
                .filter(dealPo -> dealPo.getAreaType().equals(AreaType.DISTANT_CITY.value))
                .collect(Collectors.groupingBy(DealPo::getTimeStr, Collectors.summingInt(DealPo::getNum)));

        List<String> list = dealList.stream()
                .sorted(Comparator.comparing(DealPo::getTimeStr))
                .map(DealPo::getTimeStr)
                .distinct()
                .collect(Collectors.toList());
        for(String item : list){
            result.get("X").add(item);
            result.get("newHourse").add(map1.get(item));
            result.get("secondHourse").add(map2.get(item));
            result.get("newHourseMainCity").add(map3.get(item));
            result.get("newHourseDistantCity").add(map4.get(item));
            result.get("secondHourseMainCity").add(map5.get(item));
            result.get("secondHourseDistantCity").add(map6.get(item));
        }

        return ResultPo.success(result);
    }
}
