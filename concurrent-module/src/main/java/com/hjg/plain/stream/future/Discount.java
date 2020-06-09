package com.hjg.plain.stream.future;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10),
        PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        String threadName = Thread.currentThread().getName();
        System.out.println("applyDiscount threadName = " + threadName
                + ", price = " + quote.getPrice() + ", code = " + quote.getDiscountCode());

        return quote.getShopName() + " price is " +
                Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        return (price * (100 - code.percentage) / 100);
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
