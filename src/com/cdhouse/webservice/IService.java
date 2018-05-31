package com.cdhouse.webservice;

import com.cdhouse.po.DealPo;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService {

    /**
     * 查询总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime);

    /**
     * 查询预售信息
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PreSalePo> queryPreSaleInfo(String startTime, String endTime);

    /**
     * 查询交易信息
     * @param startTime
     * @param endTime
     * @return
     */
    public List<DealPo> queryDealInfo(String startTime, String endTime);
}
