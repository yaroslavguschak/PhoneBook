package com.github.yaroslavguschak.config;

import com.github.yaroslavguschak.DAO.ContactDAO;
import com.github.yaroslavguschak.DAO.UserDAO;
import com.github.yaroslavguschak.DAO.mysql.ContactDAOMySQL;
import com.github.yaroslavguschak.DAO.mysql.UserDAOMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/*init MySQL DB*/
@Configuration
@Profile("mysql")
public class ProfileApplicationConfMySQL {
    @Autowired
    Environment env;

    @Autowired
    UserDAOMySQL userDAOMySQL;

    @Autowired
    ContactDAOMySQL contactDAOMySQL;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("jdbc.driverClassName"));
        dataSourceBuilder.url(env.getProperty("jdbc.url"));
        dataSourceBuilder.username(env.getProperty("jdbc.username"));
        dataSourceBuilder.password(env.getProperty("jdbc.password"));
        return dataSourceBuilder.build();
    }


}
