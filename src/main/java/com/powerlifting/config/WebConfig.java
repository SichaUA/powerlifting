package com.powerlifting.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import freemarker.template.TemplateException;
import jdk.nashorn.internal.parser.JSONParser;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
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

    @Bean
    FreeMarkerViewResolver freeMarkerViewResolver() {
        final FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();

        // TODO Change this in future

//        freeMarkerViewResolver.setContentType("text/html");
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setPrefix("");
        freeMarkerViewResolver.setSuffix(".ftl");

        return freeMarkerViewResolver;
    }

//    @Bean Gson serializer() {
//        return new Gson();
//    }

    @Bean Gson serializer() {
        return new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    }

    @Bean JsonParser jsonParser() {
        return new JsonParser();
    }
}
