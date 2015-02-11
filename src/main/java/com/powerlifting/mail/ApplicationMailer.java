package com.powerlifting.mail;


import com.sun.xml.internal.ws.client.SenderException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Service("mailService")
public class ApplicationMailer {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    @Autowired
    private Configuration configuration;

    /**
     * This method will send compose and send the message
     * */
    public void sendMail(String to, String subject, String template, Map model) throws SenderException
    {
        SimpleMailMessage message = new SimpleMailMessage();

        final String result;
        try{
            result = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template), model);
        }catch (IOException e){
            throw new SenderException("Unable to read or process freemarker configuration or template", e);
        }catch (TemplateException e) {
            throw new SenderException("Problem initializing freemarker or rendering template '"
                    + template + "'", e);
        }

        message.setTo(to);
        message.setFrom("powerlifting.service@gmail.com");
        message.setSubject(subject);
        message.setText(result);
        mailSender.send(message);
    }

    /**
     * This method will send a simple message
     * */
    public void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("powerlifting.service@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}