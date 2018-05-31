package com.cdhouse.dao;

import com.cdhouse.po.DealPo;
import com.cdhouse.po.SumPo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IDealDao {

    /**
     * 保存交易信息
     * @param dealPo
     * @return
     * @throws DataAccessException
     */
    public int addDeal(DealPo dealPo) throws DataAccessException;

    /**
     * 查询交易面积总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime) throws DataAccessException;

    /**
     * 查询交易信息
     * @param startTime
     * @param endTime
     * @return
     * @throws DataAccessException
     */
    public List<DealPo> queryDealInfo(String startTime, String endTime) throws DataAccessException;

}
