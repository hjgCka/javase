package com.hjg.jackson.example.model;

import lombok.Data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
@Data
public class ExternalizableBook implements Externalizable {

    private static final long serialVersionUID = -5702934849836426516L;

    private String name;
    private Date date;
    private transient BigDecimal price;

    public ExternalizableBook(){

    }

    /**
     * 当使用Externalizable接口时，必须提供无参构造函数，且不支持@ConstructorProperties注解。
     * @param name
     * @param date
     * @param price
     */
    public ExternalizableBook(String name, Date date, BigDecimal price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeObject(date);
        out.writeObject(price);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.date = (Date) in.readObject();
        this.price = (BigDecimal) in.readObject();
    }
}
