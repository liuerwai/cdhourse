package com.cdhouse.po;

import com.alibaba.fastjson.JSON;
import com.cdhouse.utils.DateUtils;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import java.util.Date;

public class DealPo {

    // 区域编号
    private String areaId;
    // 区域名称
    private String areaName;
    // 区域类型
    private String areaType;
    // 销售面积
    private Float coverage;
    // 交易时间
    private Date time;
    // 交易时间str
    private String timeStr;
    // 1：新房、2:二手房
    private String type;
    // 数量
    private Integer num;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Float getCoverage() {
        return coverage;
    }

    public void setCoverage(Float coverage) {
        this.coverage = coverage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        this.timeStr = DateUtils.getDateFormat().format(time);
    }

    public String getTimeStr() {
        return timeStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString(){

        return JSON.toJSONString(this);
    }
}
