package com.cdhouse.springconfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@PropertySource("classpath:mysql.properties")
@Configuration
@ComponentScan("com.cdhouse")
public class RootConfig {

    @Value("${mysql.jdbcUrl}")
    private String jdbcUrl;
    @Value("${mysql.user}")
    private String user;
    @Value("${mysql.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws Exception {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setJdbcUrl(jdbcUrl);
//        dataSource.setUser(user);
//        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost/cdhouse?characterEncoding=UTF-8");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        System.out.println(password);
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{

        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());
        return template;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws Exception{

        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }

}
