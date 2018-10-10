package com.cdhouse.dao.impl2;

import com.cdhouse.contans.AreaType;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import com.cdhouse.serialize.SerializerUtills;
import com.cdhouse.utils.DateUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
public class PreSaleDaoSerilizeImpl implements IPreSaleDao {

    @Override
    public int addPreSale(PreSalePo preSalePo) throws DataAccessException {

        Map<String, PreSalePo> map = SerializerUtills.getPresale();
        map.put(preSalePo.getKey(), preSalePo);
        return SerializerUtills.savePresale(map);
    }

    @Override
    public SumPo querySum(String startTime, String endTime) throws DataAccessException {

        SumPo sumPo = new SumPo();
        List<Date> listQueryDate = DateUtils.transQueryStartEndDate(startTime, endTime);
        Date startDate = listQueryDate.get(0);
        Date endDate = listQueryDate.get(1);
        Optional<Float> optional = SerializerUtills.getPresale().values().stream()
                .filter(preSalePo -> preSalePo.getTime().compareTo(startDate) >= 0
                        && preSalePo.getTime().compareTo(endDate) <= 0)
                .map(PreSalePo::getCoverage)
                .reduce(Float::sum);
        sumPo.setPreSaleAllSum(optional.orElse(0f)/100 + "");
        optional = SerializerUtills.getPresale().values().stream()
                .filter(preSalePo -> preSalePo.getTime().compareTo(startDate) >= 0
                        && preSalePo.getTime().compareTo(endDate) <= 0)
                .filter(preSalePo -> preSalePo.getAreaType().equals(AreaType.MAIN_CITY.value))
                .map(PreSalePo::getCoverage)
                .reduce(Float::sum);
        sumPo.setPreSaleMainCitySum(optional.orElse(0f)/100 + "");
        return sumPo;
    }

    @Override
    public List<PreSalePo> queryPreSale(String startTime, String endTime) throws DataAccessException {

        List<Date> listQueryDate = DateUtils.transQueryStartEndDate(startTime, endTime);
        Date startDate = listQueryDate.get(0);
        Date endDate = listQueryDate.get(1);
        List<PreSalePo> list = SerializerUtills.getPresale().values().stream()
                .filter(preSalePo -> preSalePo.getTime().compareTo(startDate) >= 0
                        && preSalePo.getTime().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(PreSalePo::getTime).reversed())
                .collect(Collectors.toList());
        return list;
    }
}
