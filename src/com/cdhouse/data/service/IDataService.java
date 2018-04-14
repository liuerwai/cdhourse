package com.cdhouse.data.service;

import org.springframework.stereotype.Component;

@Component
public interface IDataService {

    /**
     * 爬取预售信息
     */
    public void crawelPreSaleInfo() throws Exception;

    /**
     * 爬取预售信息
     */
    public void crawelDealInfo() throws Exception;

}
