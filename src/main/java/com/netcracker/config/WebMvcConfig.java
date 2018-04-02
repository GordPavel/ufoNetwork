package com.netcracker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
class WebMvcConfig extends WebMvcConfigurationSupport{

    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String MESSAGE_SOURCE     = "/WEB-INF/messages";
    private static final String VIEWS              = "/WEB-INF/pages/";
    private static final String viewType           = ".jsp";

    private static final String RESOURCES_LOCATION = "/resources/";
    private static final String RESOURCES_HANDLER  = RESOURCES_LOCATION + "**";

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch( false );
        requestMappingHandlerMapping.setUseTrailingSlashMatch( false );
        return requestMappingHandlerMapping;
    }

    @Bean( name = "messageSource" )
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename( MESSAGE_SOURCE );
        messageSource.setCacheSeconds( 5 );
        messageSource.setDefaultEncoding( CHARACTER_ENCODING );
        return messageSource;
    }

    @Bean
    public ViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass( JstlView.class );
        bean.setPrefix( VIEWS );
        bean.setSuffix( viewType );
        return bean;
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ){
        registry.addResourceHandler( RESOURCES_HANDLER ).addResourceLocations( RESOURCES_LOCATION );
    }

    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer ){
        configurer.enable();
    }

}
