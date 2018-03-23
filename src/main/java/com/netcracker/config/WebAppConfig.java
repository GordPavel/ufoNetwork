package com.netcracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@ComponentScan( "com.netcracker" )
@PropertySource( "classpath:jpa.properties" )
public class WebAppConfig{

    //    Access to all properties from jpa.properties file
    @Resource private Environment env;

    @Bean
    public UrlBasedViewResolver setupViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix( "/WEB-INF/pages/" );
        resolver.setSuffix( ".jsp" );
        resolver.setViewClass( JstlView.class );
        return resolver;
    }
}
