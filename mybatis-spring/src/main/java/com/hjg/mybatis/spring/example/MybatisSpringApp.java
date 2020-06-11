package com.hjg.mybatis.spring.example;

import com.hjg.mybatis.spring.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MybatisSpringApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(new Class[]{AppConfig.class});


    }
}
