package com.cdhouse.utils;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Priority;

public class MyLogger extends FileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        //只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }


}
