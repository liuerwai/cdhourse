package com.cdhouse.po;


import org.apache.commons.lang.StringUtils;

public class ResultPo {

    private Boolean status;
    private Object records;
    private String message;
    private int queryRecordCount;
    private int totalRecordCount;

    public static  ResultPo success(Object records, String message){
        ResultPo result = new ResultPo();
        if(StringUtils.isNotBlank(message)){
            result.setMessage(message);
        } else {
            result.setMessage("操作成功");
        }
        result.records = records;
        result.status = true;
        return result;
    }

    public static  ResultPo success(Object records){
        return success(records, null);
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

    public int getQueryRecordCount() {
        return queryRecordCount;
    }

    public void setQueryRecordCount(int queryRecordCount) {
        this.queryRecordCount = queryRecordCount;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }
}
