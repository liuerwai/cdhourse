package com.cdhouse.po;

import com.cdhouse.utils.DateUtils;

import java.util.Date;

public class PreSalePo {

    // 区域编号
    private String areaId;
    // 区域名称
    private String areaName;
    // 区域名称
    private String areaType;
    // 预售小区名称
    private String valligeName;
    // 预售面积
    private Float coverage;
    // 预售时间
    private Date time;
    // 预售时间str
    private String timeStr;

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

    public String getValligeName() {
        return valligeName;
    }

    public void setValligeName(String valligeName) {
        this.valligeName = valligeName;
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

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String toString(){

        return "区域编号: " + areaId + "区域名称: "  + areaName + "区域l类型: "
                + areaType + "小区名称: " + valligeName + "预售面积" + coverage+ "预售时间" + timeStr;
    }
}
