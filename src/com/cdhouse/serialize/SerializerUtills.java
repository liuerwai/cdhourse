package com.cdhouse.serialize;

import com.cdhouse.po.DealPo;
import com.cdhouse.po.PreSalePo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializerUtills {

    public final static String presalePath = SerializerUtills.class.getClassLoader().getResource("").getPath() + "/presaleMap.ser";
    public final static String dealPath = SerializerUtills.class.getClassLoader().getResource("").getPath() + "/dealPathMap.ser";

    public static void main(String[] args) {

        try {
            List list = new ArrayList();
            PreSalePo preSalePo = new PreSalePo();
            preSalePo.setAreaId("1");
            list.add(preSalePo);
            save(list, presalePath);
            List<PreSalePo> list1 = (List<PreSalePo>) get(presalePath);
            System.out.println(list.size());
            preSalePo = list1.get(0);
            System.out.println(preSalePo.getAreaId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存序列化对象
     *
     * @param object
     * @param path
     * @return
     */
    public static synchronized int save(Object object, String path) {

        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 查询序列化对象
     *
     * @param path
     * @return
     */
    public static synchronized Object get(String path) {

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object object = in.readObject();
            in.close();
            fileIn.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取预售信息
     *
     * @return
     */
    public static Map<String, PreSalePo> getPresale() {

        Map<String, PreSalePo> map = (Map<String, PreSalePo>) SerializerUtills.get(SerializerUtills.presalePath);
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 保存预售信息
     *
     * @param object
     * @return
     */
    public static int savePresale(Object object) {

        return save(object, presalePath);
    }

    /**
     * 获取交易信息
     *
     * @return
     */
    public static Map<String, DealPo> getDeal() {

        Map<String, DealPo> map = (Map<String, DealPo>) SerializerUtills.get(SerializerUtills.dealPath);
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 保存交易信息
     *
     * @param object
     * @return
     */
    public static int saveDeal(Object object) {

        return save(object, dealPath);
    }

}
