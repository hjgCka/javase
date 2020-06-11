package com.hjg.mybatis.spring.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource({"/com/hjg/jdbc/jdbc.properties"})
public class AppConfig {

    /**
     * @PropertySource注解将外部属性值赋值到这个对象。
     */
    @Autowired
    Environment environment;

    /**
     * 支持@Value的占位符，从Environment及其属性源解析占位符。
     * @return
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcProperties jdbcProperties() {
        return new JdbcProperties();
    }

    @Bean
    public DataSource dataSource(JdbcProperties jdbcProperties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcProperties.getUrl());
        config.setUsername(jdbcProperties.getUsername());
        config.setPassword(jdbcProperties.getPassword());
        /*config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");*/

        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}
