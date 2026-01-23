package com.weixiao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送验证码邮件
     *
     * @param to      收件人邮箱
     * @param code    验证码
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendVerificationCode(String to, String code, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content + " 验证码: " + code);
        
        mailSender.send(message);
    }

    /**
     * 发送注册验证码邮件
     *
     * @param to   收件人邮箱
     * @param code 验证码
     */
    public void sendRegisterVerificationCode(String to, String code) {
        String subject = "注册验证码";
        String content = "感谢您注册我们的网站，您的验证码是: " + code + "，请在10分钟内使用。";
        sendVerificationCode(to, code, subject, content);
    }

    /**
     * 发送登录验证码邮件
     *
     * @param to   收件人邮箱
     * @param code 验证码
     */
    public void sendLoginVerificationCode(String to, String code) {
        String subject = "登录验证码";
        String content = "您正在尝试登录，您的验证码是: " + code + "，请在10分钟内使用。";
        sendVerificationCode(to, code, subject, content);
    }

    /**
     * 发送重置密码验证码邮件
     *
     * @param to   收件人邮箱
     * @param code 验证码
     */
    public void sendResetPasswordVerificationCode(String to, String code) {
        String subject = "重置密码验证码";
        String content = "您正在尝试重置密码，您的验证码是: " + code + "，请在10分钟内使用。";
        sendVerificationCode(to, code, subject, content);
    }
}