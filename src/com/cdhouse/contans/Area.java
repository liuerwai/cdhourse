package com.cdhouse.contans;

public enum Area {

    QING_YANG("1", "青羊区", AreaType.MAIN_CITY.value),
    JIN_NIU("2", "金牛区", AreaType.MAIN_CITY.value),
    WU_HOU("3", "武侯区", AreaType.MAIN_CITY.value),
    CHENG_HUA("4", "成华区", AreaType.MAIN_CITY.value),
    GAO_XIN("5", "高新区", AreaType.MAIN_CITY.value),
    TIAN_FU_XIN_QU("6", "天府新区", AreaType.DISTANT_CITY.value),
    LONG_QUAN_YI("7", "龙泉驿区", AreaType.DISTANT_CITY.value),
    XIN_DU("8", "新都区", AreaType.DISTANT_CITY.value),
    WEN_JIANG("9", "温江区", AreaType.DISTANT_CITY.value),
    SHUANG_LIU("10", "双流区", AreaType.DISTANT_CITY.value),
    JIN_JIANG("11", "锦江区", AreaType.MAIN_CITY.value),
    JIAN_YANG("12", "简阳市", AreaType.DISTANT_CITY.value),
    DU_JIANG_YAN("13", "都江堰市", AreaType.DISTANT_CITY.value),
    QIONG_ZHOU("14", "邛崃市", AreaType.DISTANT_CITY.value),
    PEN_ZHOU("15", "彭州市", AreaType.DISTANT_CITY.value),
    CHONG_ZHOU("16", "崇州市", AreaType.DISTANT_CITY.value),
    JIN_TANG("17", "金堂县", AreaType.DISTANT_CITY.value),
    PI_DU("18", "郫都区", AreaType.DISTANT_CITY.value),
    DA_YI("19", "大邑县", AreaType.DISTANT_CITY.value),
    PU_JIANG("20", "蒲江县", AreaType.DISTANT_CITY.value),
    XIN_JIN("21", "新津县", AreaType.DISTANT_CITY.value),
    OTHER("22", "其他", AreaType.MAIN_CITY.value);

    public String  value;
    public String name;
    public String type;

     Area(String value, String name, String type){
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public static Area getEnum(String name){

         for(Area item : Area.values()){
             if(item.name.contains((name))){
                 return item;
             }
             if(name.contains((item.name))){
                 return item;
             }
         }
         return OTHER;
    }

}
