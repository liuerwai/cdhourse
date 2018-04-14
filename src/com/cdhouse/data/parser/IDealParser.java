package com.cdhouse.data.parser;

import com.cdhouse.po.DealPo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IDealParser {

    /**
     * 解析html
     * @param content
     * @return
     * @throws Exception
     */
    public List<DealPo> parse(String content) throws Exception;

}

