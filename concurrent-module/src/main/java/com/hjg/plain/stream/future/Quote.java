package com.hjg.plain.stream.future;

import lombok.Getter;

public class Quote {

    @Getter
    private final String shopName;
    @Getter
    private final double price;
    @Getter
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String threadName = Thread.currentThread().getName();


        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);

        System.out.println("parse threadName = " + threadName + ", price = " + price + ", code = " + discountCode);

        return new Quote(shopName, price, discountCode);
    }
}
