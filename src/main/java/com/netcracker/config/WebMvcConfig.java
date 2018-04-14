package com.netcracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport{

    @Override
    public void configureMessageConverters( List<HttpMessageConverter<?>> converters ){
        converters.add( new MappingJackson2HttpMessageConverter() );
        super.configureMessageConverters( converters );
    }

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch( false );
        requestMappingHandlerMapping.setUseTrailingSlashMatch( false );
        return requestMappingHandlerMapping;
    }

    @Bean( name = "multipartResolver" )
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile( ( long ) ( 10 * Math.pow( 1024 , 2 ) ) ); //10MB
        resolver.setDefaultEncoding( "UTF-8" );
        resolver.setResolveLazily( true );
        return resolver;
    }

//    @Bean( name = "messageSource" )
//    public MessageSource messageSource(){
//        ReloadableResourceBundleMessageSource messageSource =
//                new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename( MESSAGE_SOURCE );
//        messageSource.setCacheSeconds( 5 );
//        messageSource.setDefaultEncoding( CHARACTER_ENCODING );
//        return messageSource;
//    }

    @Bean
    public ViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass( JstlView.class );
        bean.setPrefix( "/pages/" );
        bean.setSuffix( ".jsp" );
        return bean;
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ){
        String resources = "/resources/";
        registry.addResourceHandler( resources + "**" )
                .addResourceLocations( resources )
                .setCachePeriod( ( int ) Duration.ofDays( 1 ).getSeconds() );
    }

    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer ){
        configurer.enable();
    }

}
