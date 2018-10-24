package com.cdhouse.kafka;

import org.springframework.beans.factory.annotation.Value;

//@Component
//@PropertySource("classpath:application.properties")
public class KafkaUtils {

    @Value("${kafka.bootstrap.servers}")
    public static String servers;


}
