package com.cdhouse.dao.impl2;

import com.cdhouse.contans.HourseType;
import com.cdhouse.dao.IDealDao;
import com.cdhouse.po.DealPo;
import com.cdhouse.po.SumPo;
import com.cdhouse.serialize.SerializerUtills;
import com.cdhouse.utils.DateUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Primary
public class DealDaoSerializeImpl implements IDealDao {

    @Override
    public int addDeal(DealPo dealPo) throws DataAccessException {

        Map map = SerializerUtills.getDeal();
        map.put(dealPo.getKey(), dealPo);
        return SerializerUtills.saveDeal(map);
    }

    @Override
    public SumPo querySum(String startTime, String endTime) throws DataAccessException {

        SumPo sumPo = new SumPo();
        List<Date> listQueryDate = DateUtils.transQueryStartEndDate(startTime, endTime);
        Date startDate = listQueryDate.get(0);
        Date endDate = listQueryDate.get(1);
        Map<String, Integer> map = SerializerUtills.getDeal().values().stream()
                .filter(dealPo -> dealPo.getTime().compareTo(startDate) >= 0
                        && dealPo.getTime().compareTo(endDate) <= 0)
                .collect(Collectors.groupingBy(DealPo::getType, Collectors.summingInt(DealPo::getNum)));
        sumPo.setDealNewSum(map.containsKey(HourseType.NEW.value) ? map.get(HourseType.NEW.value).toString() : "0");
        sumPo.setDealSecondSum(map.containsKey(HourseType.SECOEND.value) ? map.get(HourseType.SECOEND.value).toString() : "0");
        return sumPo;
    }

    @Override
    public List<DealPo> queryDealInfo(String startTime, String endTime) throws DataAccessException {

        List<Date> listQueryDate = DateUtils.transQueryStartEndDate(startTime, endTime);
        Date startDate = listQueryDate.get(0);
        Date endDate = listQueryDate.get(1);
        List<DealPo> list = SerializerUtills.getDeal().values().stream()
                .filter(dealPo -> dealPo.getTime().compareTo(startDate) >= 0
                        && dealPo.getTime().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(DealPo::getTime).reversed())
                .collect(Collectors.toList());
        return list;
    }
}
