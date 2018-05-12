package com.cdhouse.dao;

import com.cdhouse.po.SumPo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import com.cdhouse.po.PreSalePo;

import java.util.List;

@Component
public interface IPreSaleDao {

    /**
     * 保存预售信息
     * @param preSalePo
     * @return
     * @throws DataAccessException
     */
    public int addPreSale(PreSalePo preSalePo) throws DataAccessException;

    /**
     * 查询预售面积总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime) throws DataAccessException;

    /**
     * 查询预售信息
     * @param startTime
     * @param endTime
     * @return
     * @throws DataAccessException
     */
    public List<PreSalePo> queryPreSale(String startTime, String endTime) throws DataAccessException;
}
