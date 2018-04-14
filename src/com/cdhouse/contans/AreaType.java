package com.cdhouse.contans;

public enum AreaType {

    MAIN_CITY("1", "主城"),
    DISTANT_CITY("2", "郊区"),
    OTHER("3", "未知");

    public String  value;
    public String name;

    AreaType(String value, String name){
        this.value = value;
        this.name = name;
    }

    public static AreaType getEnum(String name){

        for(AreaType item : AreaType.values()){
            if(item.name.contains((name))){
                return item;
            }
        }
        return OTHER;
    }

}
