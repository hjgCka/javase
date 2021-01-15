package com.hjg.jackson.example.model;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
@ToString
public class ImmutableBook implements Serializable {
    @Getter
    private String name;
    @Getter
    private Date date;
    @Getter
    private transient BigDecimal price;

    /**
     * 对于这种无继承关系的类，没有无参构造函数也能正常序列化。
     * @param name
     * @param date
     * @param price
     */
    public ImmutableBook(String name, Date date, BigDecimal price) {
        this.name = name;
        this.date = date;
        this.price = price;
        System.out.println("构造immutableBook");
    }


}
