package com.hjg.jackson.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
@ToString
public class ImmutableJsonBook {
    @Getter
    private String name;
    @Getter
    private Date date;
    @Getter
    private transient BigDecimal price;

    /**
     * @JsonProperty的值需要与JSON的key匹配。ImmutableJsonBook的属性名称怎样无所谓。
     * @param name
     * @param date
     * @param price
     */
    @JsonCreator
    public ImmutableJsonBook(@JsonProperty("name") String name, @JsonProperty("date") Date date, @JsonProperty("price") BigDecimal price) {
        this.name = name;
        this.date = date;
        this.price = price;
        System.out.println("构造ImmutableJsonBook");
    }
}
