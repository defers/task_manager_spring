package com.defers.taskmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class SpringConfig {

    @Autowired
    EntityManagerFactory factory;
    @Autowired
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager() {

        JpaTransactionManager tm =
                new JpaTransactionManager();
        tm.setEntityManagerFactory(factory);
        tm.setDataSource(dataSource);
        return tm;

    }


}
