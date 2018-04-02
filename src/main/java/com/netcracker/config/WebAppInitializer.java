package com.netcracker.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected String[] getServletMappings(){
        return new String[]{ "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses(){
        return new Class<?>[]{ ApplicationConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses(){
        return null;
    }
}