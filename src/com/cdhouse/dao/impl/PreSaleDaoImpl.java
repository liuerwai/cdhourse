package com.cdhouse.dao.impl;

import com.cdhouse.contans.AreaType;
import com.cdhouse.contans.Table;
import com.cdhouse.dao.IPreSaleDao;
import com.cdhouse.po.PreSalePo;
import com.cdhouse.po.SumPo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;

@Component
public class PreSaleDaoImpl implements IPreSaleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String getTalbeName(){
        return Table.PRE_SALE_INFO.value;
    }

    /**
     * 增加预售信息
     * @param preSalePo
     * @return
     * @throws DataAccessException
     */
    public int addPreSale(PreSalePo preSalePo) throws DataAccessException {

        StringBuffer sql = new StringBuffer("");
        sql.append("INSERT INTO ").append(getTalbeName())
                .append("(AREA_ID, AREA_NAME, AREA_TYPE, VALLIGE_NAME, COVERAGE, TIME)")
                .append("VALUES(?, ?, ?, ?, ?, ?)");
        String[] args = new String[]{
                preSalePo.getAreaId(),
                preSalePo.getAreaName(),
                preSalePo.getAreaType(),
                preSalePo.getValligeName(),
                preSalePo.getCoverage().toString(),
                preSalePo.getTimeStr()
        };
        int[] argTypes = new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.FLOAT,
                Types.DATE,
        };
        return jdbcTemplate.update(sql.toString(), args, argTypes);
    }

    /**
     * 查询交易面积总量
     * @param startTime
     * @param endTime
     * @return
     */
    public SumPo querySum(String startTime, String endTime) throws DataAccessException{

        String tableName = getTalbeName();
        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT SUM(COVERAGE) AS PRE_SALE_ALL_SUM,")
                .append(" SUM(CASE WHEN AREA_TYPE = ")
                .append(AreaType.MAIN_CITY.value).append(" THEN COVERAGE ELSE 0 END) AS PRE_SALE_MAIN_CITY_SUM FROM ")
                .append(tableName).append(" WHERE 1=1 ");
        if(StringUtils.isNotBlank(startTime)){
            sql.append( "AND TIME >= '").append(startTime).append("'");
        }
        if(StringUtils.isNotBlank(endTime)){
            sql.append( "AND TIME <= '").append(endTime).append("'");
        }

        return jdbcTemplate.queryForObject(sql.toString(),  new BeanPropertyRowMapper<>(SumPo.class));
    }
}
