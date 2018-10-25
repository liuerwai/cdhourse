package com.cdhouse.kafka;

import com.cdhouse.utils.MailUtils;

public class ConsumerThread implements Runnable {

    private Consumer consumer;

    public ConsumerThread(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            consumer.receiveDealMessage();
        } catch (Exception e){
            MailUtils.sendMail("cdhourse报错", e.getMessage(), "liuxg@channelsoft.com");
        }
    }
}
