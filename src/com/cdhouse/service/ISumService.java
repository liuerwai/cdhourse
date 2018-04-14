package com.cdhouse.service;

import com.cdhouse.po.SumPo;
import org.springframework.stereotype.Service;

@Service
public interface ISumService {

    /**
     * 查询总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime);
}
