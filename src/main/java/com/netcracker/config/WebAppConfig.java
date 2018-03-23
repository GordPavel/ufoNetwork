package com.netcracker.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan( "com.netcracker" )
@EnableJpaRepositories( "com.netcracker.repository" )
@PropertySource( "classpath:jpa.properties" )
public class WebAppConfig{
    @Resource private Environment env;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName( env.getRequiredProperty( "db.driver" ) );
        dataSource.setUrl( env.getRequiredProperty( "db.url" ) );
        dataSource.setUsername( env.getRequiredProperty( "db.username" ) );
        dataSource.setPassword( env.getRequiredProperty( "db.password" ) );
        dataSource.setSchema( env.getRequiredProperty( "db.schema" ) );

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource( dataSource() );
        entityManagerFactoryBean.setPersistenceProviderClass( HibernatePersistenceProvider.class );
        entityManagerFactoryBean.setPackagesToScan( env.getRequiredProperty( "model.package" ) );

        entityManagerFactoryBean.setJpaProperties( hibernateProperties() );

        return entityManagerFactoryBean;
    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put( "hibernate.dialect" , env.getRequiredProperty( "db.dialect" ) );
        properties.put( "hibernate.show_sql" , env.getRequiredProperty( "db.show_sql" ) );
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
        return transactionManager;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix( "/WEB-INF/pages/" );
        resolver.setSuffix( ".jsp" );
        resolver.setViewClass( JstlView.class );
        return resolver;
    }
}
