package com.cdhouse.contans;

public enum Table {

    PRE_SALE_INFO("PRE_SALE_INFO", "预售信息表"),
    DEAL_INFO("DEAL_INFO", "交易信息表"),
    OTHER("3", "未知");

    public String  value;
    public String name;

    Table(String value, String name){
        this.value = value;
        this.name = name;
    }

    public static Table getEnum(String name){

        for(Table item : Table.values()){
            if(item.name.contains((name))){
                return item;
            }
        }
        return OTHER;
    }

}
