package com.netcracker.config;

import com.netcracker.Application;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@PropertySource( "classpath:jpa.properties" )
@EnableTransactionManagement
@EnableJpaRepositories( basePackageClasses = Application.class )
public class JpaConfig{

    @Value( "${jndi.name}" ) private         String jndiName;
    @Value( "${db.dialect}" ) private        String dialect;
    @Value( "${db.default.schema}" ) private String schema;
    @Value( "${db.show_sql}" ) private       String showSql;

    @Bean
    public DataSource dataSource() throws NamingException{
        return ( DataSource ) new JndiTemplate().lookup( jndiName );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory( DataSource dataSource ){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource( dataSource );

        String entities   = ClassUtils.getPackageName( Application.class );
        String converters = ClassUtils.getPackageName( Jsr310JpaConverters.class );
        entityManagerFactoryBean.setPackagesToScan( entities , converters );

        entityManagerFactoryBean.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );

        Properties jpaProperties = new Properties();
        jpaProperties.put( Environment.DIALECT , dialect );
        jpaProperties.put( Environment.SHOW_SQL , showSql );
        jpaProperties.put( Environment.DEFAULT_SCHEMA , schema );
        entityManagerFactoryBean.setJpaProperties( jpaProperties );

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory ){
        return new JpaTransactionManager( entityManagerFactory );
    }
}

