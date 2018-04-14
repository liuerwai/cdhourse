package com.cdhouse.po;


import org.apache.commons.lang.StringUtils;

public class ResultPo {

    private Boolean status;
    private Object rows;
    private String message;

    public static  ResultPo success(Object rows, String message){
        ResultPo result = new ResultPo();
        if(StringUtils.isNotBlank(message)){
            result.setMessage(message);
        } else {
            result.setMessage("操作成功");
        }
        result.rows = rows;
        result.status = true;
        return result;
    }

    public static  ResultPo success(Object rows){
        return success(rows, null);
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

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
