package com.wecon.box.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zengzhipeng on 2017/8/4.
 */
public class EmailUtil {

    public static final String HOST = "smtp.exmail.qq.com";
    public static final String PROTOCOL = "smtp";
    public static final int PORT = 25;
    public static final String FROM = "piapp@we-con.com.cn";//发件人的email
    public static final String PWD = "Weconhmi123";//发件人密码

    /**
     * 获取Session
     *
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol", PROTOCOL);//设置协议
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth", "true");

        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }

        };
        Session session = Session.getDefaultInstance(props, authenticator);

        return session;
    }

    public static void send(String toEmail, String subject, String content) {
        Session session = getSession();
        try {
//            System.out.println("--send--" + content);
            // Instantiate a message
            Message msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            String[] toEmailArray = toEmail.split(";");
            InternetAddress[] address = new InternetAddress[toEmailArray.length]; //{new InternetAddress(toEmail)};
            for (int i = 0; i < toEmailArray.length; i++) {
                address[i] = new InternetAddress(toEmailArray[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(content, "text/html;charset=utf-8");

            //Send the message
            Transport.send(msg);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        send("86779961@qq.com", "test email", "这个是一封测试<br>邮件");
        System.out.println("send complete");
    }
}
