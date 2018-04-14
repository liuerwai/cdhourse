package com.cdhouse.data.parser.impl;

import com.cdhouse.contans.Area;
import com.cdhouse.data.parser.IPreSaleParser;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.utils.DateUtils;
import com.cdhouse.utils.LoggerUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PreSaleParserImpl implements IPreSaleParser {

    /**
     * 解析
     * @param content
     * @return
     * @throws Exception
     */
    public List<PreSalePo> parse(String content) throws Exception {

        List<PreSalePo> list = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Element tbody = doc.getElementById("ID_ucShowNew_gridView");
        for(Element tr : tbody.getElementsByTag("tr")){
            Elements tds = tr.getElementsByTag("td");
            if(tds != null && tds.size() > 0){
                try {
                    PreSalePo preSale = new PreSalePo();
                    // 区域信息
                    Area area = Area.getEnum(tds.get(2).text());
                    preSale.setAreaId(area.value);
                    preSale.setAreaName(area.name);
                    preSale.setAreaType(area.type);
                    // 小区名称
                    preSale.setValligeName(tds.get(1).text());
                    // 面积
                    preSale.setCoverage(Float.valueOf(tds.get(6).text()));
                    // 时间
                    if(tds.get(7).text().contains("现")){
                        preSale.setTime(new Date());
                    } else {
                        preSale.setTime(DateUtils.getDateFormat().parse(tds.get(7).text()));
                    }
                    list.add(preSale);
                } catch (Exception e){
                    e.getMessage();
                    LoggerUtils.error("解析预售信息报错：" + tds.text());
                }
            }
        }
        return list;
    }
}
