package com.xiafei.springboot.starter.autoconfig.mail;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/6/12</P>
 * <P>UPDATE DATE: 2018/6/12</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.8.0
 */
public class SendMail {

    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private MailProperties mailProperties;

    /**
     * 发送纯文本的简单邮件.
     *
     * @param sendTo  接收方
     * @param text    邮件正文内容
     * @param subject 邮件主题
     */
    public void sendTextMail(final String sendTo, final String text, final String subject) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }


    /**
     * 发送html格式的邮件.
     *
     * @param sendTo  接收方
     * @param html    邮件正文html格式代码
     * @param subject 邮件主题
     * @throws Exception
     */
    public void sendHtmlMail(final String sendTo, final String html, final String subject) throws Exception {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(html, true);
        javaMailSender.send(mimeMessage);

    }

    /**
     * 发送带附件的邮件.
     *
     * @param sendTo  接收方
     * @param html    邮件正文html格式
     * @param subject 主题
     * @param fileMap 附件map，key是文件名，value是输入流资源
     * @throws Exception
     */
    public void sendAttachmentsMail(final String sendTo, final String html, final String subject,
                                    final Map<String, InputStreamSource> fileMap) throws Exception {

        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(html, true);
        if (fileMap != null && !fileMap.isEmpty()) {
            for (Map.Entry<String, InputStreamSource> entry : fileMap.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
        }
        javaMailSender.send(mimeMessage);

    }

    public void sendInlineMail(final String[] sendTo, final String html, final String subject,
                               final Map<String, org.springframework.core.io.Resource> fileMap) throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(html, true);

        if (fileMap != null && !fileMap.isEmpty()) {
            for (Map.Entry<String, org.springframework.core.io.Resource> entry : fileMap.entrySet()) {
                helper.addInline(entry.getKey(), entry.getValue());
            }
        }
        javaMailSender.send(mimeMessage);

    }

}
