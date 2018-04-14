package com.cdhouse.data.crawl.impl;

import com.cdhouse.data.crawl.IDealCrawl;
import com.cdhouse.data.parser.impl.DealParserImel;
import com.cdhouse.utils.HttpClientUtils;
import com.cdhouse.utils.LoggerUtils;
import org.springframework.stereotype.Component;

@Component
public class DealCrawlImpl implements IDealCrawl{

    private static final String url = "http://www.cdfgj.gov.cn/SCXX/Default.aspx";

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

    public static void main(String[] agrs) throws Exception{
        DealCrawlImpl crawl = new DealCrawlImpl();
        String content = crawl.crawl();
        DealParserImel dealParserImel = new DealParserImel();
        dealParserImel.parse(content);
    }

}
