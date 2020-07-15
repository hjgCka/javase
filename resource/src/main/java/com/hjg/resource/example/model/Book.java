package com.hjg.resource.example.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book {
    String author;
    String name;
    BigDecimal price;
    Date pressDate;
}
