package com.hjg.resource.example.model;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String name;
    private int age;
    private String address;
    private Date birthday;
    private String job;
}
