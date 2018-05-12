package com.cdhouse.service;

import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISumService {

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
}
