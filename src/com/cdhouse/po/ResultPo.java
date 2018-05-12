package com.cdhouse.po;


import org.apache.commons.lang.StringUtils;

public class ResultPo {

    private Boolean status;
    private Object records;
    private String message;
    private Integer queryRecordCount;
    private Integer totalRecordCount;

    public static  ResultPo success(Object records, String message, Integer queryRecordCount, Integer totalRecordCount){

        ResultPo result = new ResultPo();
        if(StringUtils.isNotBlank(message)){
            result.setMessage(message);
        } else {
            result.setMessage("操作成功");
        }
        if(queryRecordCount != null) {
            result.setQueryRecordCount(queryRecordCount);
        }
        if(totalRecordCount != null) {
            result.setTotalRecordCount(totalRecordCount);
        }
        result.records = records;
        result.status = true;
        return result;
    }

    public static  ResultPo success(Object records){
        return success(records, null, null, null);
    }

    public static  ResultPo success(Object records, int queryRecordCount, int totalRecordCount){
        return success(records, null, queryRecordCount, totalRecordCount);
    }

    public static  ResultPo error(String message){
        ResultPo result = new ResultPo();
        if(StringUtils.isNotBlank(message)){
            result.setMessage(message);
        } else {
            result.setMessage("操作失败");
        }
        result.status = false;
        return result;
    }

    public static  ResultPo error(){
        return error(null);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRecords() {
        return records;
    }

    public void setRecords(Object records) {
        this.records = records;
    }

    public Integer getQueryRecordCount() {
        return queryRecordCount;
    }

    public void setQueryRecordCount(Integer queryRecordCount) {
        this.queryRecordCount = queryRecordCount;
    }

    public Integer getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(Integer totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }
}
