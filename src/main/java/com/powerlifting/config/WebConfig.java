package com.powerlifting.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.powerlifting.mail.ApplicationMailer;
import com.powerlifting.mail.Email;
import com.powerlifting.reports.ReportsGenerating;
import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.powerlifting.controllers" })
public class WebConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor)
                .addPathPatterns("/admin*//**//**")
                .excludePathPatterns("/sign-in");
    }*/

    @Bean FreeMarkerConfigurer freeMarkerConfigurer() {
        final FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setDefaultEncoding("UTF-8");
        factory.setTemplateLoaderPath("/templates");

        final FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        try{
//            Properties properties = new Properties();
//            properties.setProperty("locale", "uk_UA");

//            configurer.setDefaultEncoding("UTF-8");
//            configurer.setFreemarkerSettings(properties);
            configurer.setConfiguration(factory.createConfiguration());

        }catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }

        return configurer;
    }

    @Bean FreeMarkerViewResolver freeMarkerViewResolver() {
        final FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();

        // TODO Change this in future
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");

        return freeMarkerViewResolver;
    }

    @Bean FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        final FreeMarkerConfigurationFactoryBean factory = new FreeMarkerConfigurationFactoryBean();
        factory.setDefaultEncoding("UTF-8");
        factory.setTemplateLoaderPath("/WEB-INF/classes/templates/mail");

        return factory;
    }

    @Bean Gson serializer() {
        return new GsonBuilder().setDateFormat("MM/dd/yyyy").create();
    }

    @Bean JsonParser jsonParser() {
        return new JsonParser();
    }

    @Bean
    JavaMailSenderImpl mailSender () {
        //powerlifting.service@gmail.com
        //password: powerliftingpassword
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("powerlifting.service@gmail.com");
        javaMailSender.setPassword("powerliftingpassword");

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

    @Bean
    SimpleMailMessage preConfigurerMessage () {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("powerlifting.service@gmail.com");
        mailMessage.setTo("SichaUA@gmail.com");
        mailMessage.setSubject("Site powerlifting error!!");

        return mailMessage;
    }

    @Bean ApplicationMailer applicationMailer() {
        return new ApplicationMailer();
    }

    @Bean CommonsMultipartResolver multipartResolver () {
        return new CommonsMultipartResolver();
    }

    @Bean
    Email email() {
        return new Email();
    }

    @Bean
    ReportsGenerating reportsGenerating() {return new ReportsGenerating();}
}
