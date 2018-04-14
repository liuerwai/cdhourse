package com.cdhouse.data.service.impl;

import com.cdhouse.data.crawl.IDealCrawl;
import com.cdhouse.data.crawl.IPreSaleCrawl;
import com.cdhouse.dao.IDealDao;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.data.parser.IDealParser;
import com.cdhouse.data.parser.IPreSaleParser;
import com.cdhouse.data.service.IDataService;
import com.cdhouse.po.DealPo;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataServiceImpl implements IDataService {

    private final static int ERROR_NUM = 10;
    @Autowired
    IPreSaleCrawl preSaleCrawl;
    @Autowired
    IPreSaleParser preSaleParser;
    @Autowired
    IPreSaleDao preSaleDao;
    @Autowired
    IDealCrawl dealCrawl;
    @Autowired
    IDealParser dealParser;
    @Autowired
    IDealDao dealDao;

    /**
     * 爬取预售信息
     */
    public void crawelPreSaleInfo() throws Exception{

        boolean crawlResult = false;
        String content = "";
        for(int i = 0; i < ERROR_NUM; i++){
            content = preSaleCrawl.crawl();
            if(!content.equals("ERROR")){
                crawlResult = true;
            }
            if(crawlResult){
                break;
            }
        }
        if(!crawlResult){
            return;
        }
        List<PreSalePo> list = preSaleParser.parse(content);
        for(PreSalePo item : list){
            try {
                preSaleDao.addPreSale(item);
            } catch (Exception e){
                e.printStackTrace();
                LoggerUtils.error("保存预售信息报错：" + item.toString() + e.getMessage());
            }
        }
    }

    /**
     * 爬取交易信息
     * @throws Exception
     */
    public void crawelDealInfo() throws Exception {

        boolean crawlResult = false;
        String content = "";
        for(int i = 0; i < ERROR_NUM; i++){
            content = dealCrawl.crawl();
            if(!content.equals("ERROR")){
                crawlResult = true;
            }
            if(crawlResult){
                break;
            }
        }
        if(!crawlResult){
            return;
        }
        List<DealPo> list = dealParser.parse(content);
        for(DealPo item : list){
            try {
                dealDao.addDeal(item);
            } catch (Exception e){
                e.printStackTrace();
                LoggerUtils.error("保存交易信息报错：" + item.toString() + e.getMessage());
            }
        }
    }
}
