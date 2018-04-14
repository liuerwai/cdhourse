package com.cdhouse.data.parser.impl;

import com.cdhouse.contans.Area;
import com.cdhouse.contans.HourseType;
import com.cdhouse.data.parser.IDealParser;
import com.cdhouse.po.DealPo;
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
public class DealParserImel implements IDealParser {

    /**
     * 解析
     *
     * @param content
     * @return
     * @throws Exception
     */
    public List<DealPo> parse(String content) throws Exception {

        List<DealPo> list = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Element reghtContent = doc.getElementsByClass("rightContent").first();
        Element newHourseTable = reghtContent.getElementsByTag("table").first();
        Element seconendHourseTable = reghtContent.getElementsByTag("table").get(3);
        parseTable(newHourseTable, list, HourseType.NEW.value);
        parseTable(seconendHourseTable, list, HourseType.SECOEND.value);
        return list;
    }

    /**
     * 解析信息table
     *
     * @param table
     * @param list
     * @param Type
     */
    private void parseTable(Element table, List<DealPo> list, String Type) {

        try {
            for (Element tr : table.getElementsByTag("tr")) {
                Elements tds = tr.getElementsByTag("td");
                if (tds != null && tds.size() == 4) {
                    DealPo dealPo = new DealPo();
                    // 区域信息
                    Area area = Area.getEnum(tds.get(0).text());
                    dealPo.setAreaId(area.value);
                    dealPo.setAreaName(area.name);
                    dealPo.setAreaType(area.type);
                    // 面积
                    dealPo.setCoverage(Float.valueOf(tds.get(1).text()));
                    // 时间
                    dealPo.setTime(new Date());
                    // 类型
                    dealPo.setType(Type);
                    list.add(dealPo);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            LoggerUtils.error("解析交易信息报错：" + table.text());
        }
    }

}
