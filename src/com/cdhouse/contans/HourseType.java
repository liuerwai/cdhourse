package com.cdhouse.contans;

public enum HourseType {

    NEW("1", "新房"),
    SECOEND("2", "二手房"),
    OTHER("3", "未知");

    public String  value;
    public String name;

    HourseType(String value, String name){
        this.value = value;
        this.name = name;
    }

    public static HourseType getEnum(String value){

        for(HourseType item : HourseType.values()){
            if(item.value.contains((value))){
                return item;
            }
        }
        return OTHER;
    }

}
