package com.netcracker.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class Initializer implements WebApplicationInitializer{

    @Override
    public void onStartup( ServletContext servletContext ){
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        // регистрируем конфигурацию созданую высше
        ctx.register( WebMvcConfig.class );
        // добавляем в контекст слушателя с нашей конфигурацией
        servletContext.addListener( new ContextLoaderListener( ctx ) );
        ctx.setServletContext( servletContext );

        // настраиваем маппинг Dispatcher Servlet-а
        ServletRegistration.Dynamic servlet =
                servletContext.addServlet( "dispatcher" , new DispatcherServlet( ctx ) );
        servlet.addMapping( "/" );
        servlet.setAsyncSupported( true );
        servlet.setLoadOnStartup( 1 );
    }
}