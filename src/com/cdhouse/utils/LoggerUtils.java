package com.cdhouse.utils;

import org.apache.log4j.Logger;

public class LoggerUtils {

    private static Logger infoLogger = Logger.getLogger("info");
    private static Logger errorLogger = Logger.getLogger("error");
    private static Logger successLogger = Logger.getLogger("success");

    public static void info(String message){

        infoLogger.info(message);
    }

    public static void error(String message){

        errorLogger.error(message);
    }

    public static void success(String message){

        successLogger.warn(message);
    }

}
