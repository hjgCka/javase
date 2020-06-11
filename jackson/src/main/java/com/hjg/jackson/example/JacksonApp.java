package com.hjg.jackson.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjg.jackson.example.model.Person;

public class JacksonApp {

    public static void main(String[] args) throws JsonProcessingException {

        Person person = new Person();
        person.setAddress("sssss");
        person.setAge(20);
        person.setGender(1);
        person.setName("Jk");

        ObjectMapper objectMapper = new ObjectMapper();
        //如果Person带上了@JsonFilter("myFilter")注解，必须提供这个过滤器
        String str = objectMapper.writeValueAsString(person);

        System.out.println("str = " + str);

        //单个Person对象无法转换为List<Person>，除非传递List对象。
        /*List<Person> list = ConvertUtil.convert2List(person, Person.class);
        System.out.println(list.get(0));*/
    }
}
