package com.cdhouse.webservice.impl;

import com.cdhouse.dao.IDealDao;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import com.cdhouse.webservice.ISumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SumServiceImpl implements ISumService {

    @Autowired
    IPreSaleDao preSaleDao;
    @Autowired
    IDealDao dealDao;

    /**
     * 查询总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime) {

        SumPo result = new SumPo();
        SumPo presaleSum = preSaleDao.querySum(startTime, endTime);
        SumPo dealSum = dealDao.querySum(startTime, endTime);
        result.setPreSaleMainCitySum(presaleSum.getPreSaleMainCitySum());
        result.setPreSaleAllSum(presaleSum.getPreSaleAllSum());
        result.setDealNewSum(dealSum.getDealNewSum());
        result.setDealSecondSum(dealSum.getDealSecondSum());
        return result;
    }

    /**
     * 查询预售信息
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PreSalePo> queryPreSaleInfo(String startTime, String endTime) {

        return preSaleDao.queryPreSale(startTime, endTime);
    }
}
