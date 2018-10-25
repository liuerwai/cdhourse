package com.cdhouse.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

    private static JavaMailSenderImpl mailSender;

    static {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(PropertyUtils.INSTANCE.getPropertiesKey("email.username"));
        mailSender.setPassword(PropertyUtils.INSTANCE.getPropertiesKey("email.password"));
        mailSender.setPort(Integer.valueOf(PropertyUtils.INSTANCE.getPropertiesKey("email.port")));
        mailSender.setHost(PropertyUtils.INSTANCE.getPropertiesKey("email.host"));
    }

    /**
     * 发送邮件
     *
     * @param title
     * @param messageContent
     * @param email
     */
    public static void sendMail(String title, String messageContent, String email) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("liuxg@channelsoft.com");
            message.setSubject(title);
            message.setText(messageContent);
            LoggerUtils.info("发送邮件：\n{}" + messageContent);
            message.setTo(email);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
