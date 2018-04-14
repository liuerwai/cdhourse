package com.cdhouse.springconfig;

import com.cdhouse.data.crawl.IDealCrawl;
import com.cdhouse.data.crawl.impl.DealCrawlImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("com.cdhouse")
public class RootConfig {

    @Bean
    public DataSource dataSource() throws Exception {

        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/cdhouse/resource/mysql.properties");
        properties.load(inputStream);
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(properties.getProperty("mysql.jdbcUrl"));
        dataSource.setUser(properties.getProperty("mysql.user"));
        dataSource.setPassword(properties.getProperty("mysql.password"));
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
