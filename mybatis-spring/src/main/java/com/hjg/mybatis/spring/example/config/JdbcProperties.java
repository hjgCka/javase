package com.hjg.mybatis.spring.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JdbcProperties {

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.url}")
    private String url;
}
