package com.cdhouse.data.parser;

import org.springframework.stereotype.Component;
import com.cdhouse.po.PreSalePo;

import java.util.List;

@Component
public interface IPreSaleParser {

    /**
     * 解析html
     * @param content
     * @return
     * @throws Exception
     */
    public List<PreSalePo> parse(String content) throws Exception;

}
