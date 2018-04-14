package com.cdhouse.data.crawl.impl;

import com.cdhouse.data.crawl.IPreSaleCrawl;
import com.cdhouse.utils.HttpClientUtils;
import com.cdhouse.utils.LoggerUtils;
import org.springframework.stereotype.Component;

@Component
public class PreSaleCrawlImpl implements IPreSaleCrawl {

    private static final String url = "http://www.cdfgj.gov.cn/SCXX/ShowNew.aspx";

    /**
     * 爬取数据
     * @return
     */
    public String crawl() {

        try {
            String content = HttpClientUtils.doGetWithParam(url, null);
            return content;
        } catch (Exception e){
            LoggerUtils.error(e.getMessage());
            return "ERROR";
        }
    }

}
