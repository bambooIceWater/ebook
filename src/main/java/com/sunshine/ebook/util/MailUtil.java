package com.sunshine.ebook.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LMG on 2017/3/16.
 */
public class MailUtil {

    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    public boolean send(Mail mail) {
        // 发送email
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(mail.getHost());
            // 字符编码集的设置
            email.setCharset(Mail.ENCODEING);
            // 收件人的邮箱
            email.addTo(mail.getReceiver());
            // 发送人的邮箱
            email.setFrom(mail.getSender(), mail.getName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(mail.getUsername(), mail.getPassword());
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(mail.getMessage());
            // 发送
            email.send();
            if (logger.isDebugEnabled()) {
                logger.debug(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
            }
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            logger.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver()
                    + " 失败");
            return false;
        }
    }

    public boolean sendMail(int sendFlag, String receiver, String content) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("email.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mail mail = new Mail();
        mail.setHost(properties.getProperty("email.host"));
        mail.setSender(properties.getProperty("email.sender.account"));
        mail.setUsername(properties.getProperty("email.sender.account"));
        mail.setPassword(properties.getProperty("email.sender.password"));
        mail.setName(properties.getProperty("email.sender.name"));
        mail.setReceiver(receiver);
        String subject = null;
        if (sendFlag == 0) {
            //注册邮件
            subject = "用户注册";
        } else {
            //密码找回
            subject = "密码找回";
        }
        mail.setSubject(subject);
        mail.setMessage(content);
        return send(mail);
    }

    public static void main(String[] args) {
        Mail mail = new Mail();
        mail.setHost("smtp.163.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的
        mail.setSender("to_be_success@163.com");
        mail.setReceiver("495537636@qq.com"); // 接收人
        mail.setUsername("to_be_success@163.com"); // 登录账号,一般都是和邮箱名一样吧
        mail.setPassword("l;m;g;5219"); // 发件人邮箱的登录密码
        mail.setSubject("测试邮件");
        mail.setMessage("收到请回复");
        new MailUtil().send(mail);
    }

}
