package com.cdhouse.dao.impl;

import com.cdhouse.contans.HourseType;
import com.cdhouse.contans.Table;
import com.cdhouse.dao.IDealDao;
import com.cdhouse.po.DealPo;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;

@Component
public class DealDaoImpl implements IDealDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String getTalbeName(){
        return Table.DEAL_INFO.value;
    }

    /**
     * 插入交易信息
     * @param dealPo
     * @return
     * @throws DataAccessException
     */
    public int addDeal(DealPo dealPo) throws DataAccessException {

        StringBuffer sql = new StringBuffer("");
        sql.append("REPLACE INTO ").append(getTalbeName())
                .append("(AREA_ID, AREA_NAME, AREA_TYPE, COVERAGE, TIME, TYPE, NUM)")
                .append("VALUES(?, ?, ?, ?, ?, ?, ?)");
        Object[] args = new Object[]{
                dealPo.getAreaId(),
                dealPo.getAreaName(),
                dealPo.getAreaType(),
                dealPo.getCoverage().toString(),
                dealPo.getTimeStr(),
                dealPo.getType(),
                dealPo.getNum()
        };
        int[] argTypes = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.DOUBLE,
                Types.DATE,
                Types.INTEGER,
                Types.INTEGER
        };
        return jdbcTemplate.update(sql.toString(), args, argTypes);
    }

    /**
     * 查询预售面积总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime) throws DataAccessException{

        String tableName = getTalbeName();
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT ")
                .append(" SUM(CASE WHEN TYPE = ").append(HourseType.NEW.value).append(" THEN COVERAGE ELSE 0 END) AS DEAL_NEW_SUM,")
                .append(" SUM(CASE WHEN TYPE = ").append(HourseType.SECOEND.value).append(" THEN COVERAGE ELSE 0 END) AS DEAL_SECOND_SUM FROM ")
                .append(tableName).append(" WHERE 1=1 ");
        if(StringUtils.isNotBlank(startTime)){
            sql.append( "AND TIME >= '").append(startTime).append("'");
        }
        if(StringUtils.isNotBlank(endTime)){
            sql.append( "AND TIME <= '").append(endTime).append("'");
        }
        return jdbcTemplate.queryForObject(sql.toString(),  new BeanPropertyRowMapper<>(SumPo.class));
    }

    /**
     * 查询交易信息
     * @param startTime
     * @param endTime
     * @return
     * @throws DataAccessException
     */
    public List<DealPo> queryDealInfo(String startTime, String endTime) throws DataAccessException {

        String tableName = getTalbeName();
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT * FROM ").append(tableName).append(" WHERE 1=1 ");
        if(StringUtils.isNotBlank(startTime)){
            sql.append( "AND TIME >= '").append(startTime).append("'");
        }
        if(StringUtils.isNotBlank(endTime)){
            sql.append( "AND TIME <= '").append(endTime).append("'");
        }
        sql.append(" ORDER BY TIME DESC ");
        return jdbcTemplate.query(sql.toString(),  new BeanPropertyRowMapper<>(DealPo.class));
    }

}
