package com.hjg.jackson.example.model;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
@Data
public class Book implements Serializable {
    private String name;
    private Date date;
    private transient BigDecimal price;

    //这2个方法在一定程度打破了默认的序列化机制，并且将transient字段进行了序列化
    private void writeObject(ObjectOutputStream out) throws IOException {
        //对非transient和static的字段进行序列化
        out.defaultWriteObject();
        out.writeObject(price);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.price = (BigDecimal) in.readObject();
    }
}
