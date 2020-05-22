package com.hjg.plain.future;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@AllArgsConstructor
public class Shop {

    @Getter
    @Setter
    private String name;

    private static Random random = new Random();

    public Future<Double> getPriceSync1(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                //正常计算结束，完成Future操作并设置商品价格
                futurePrice.complete(price);
            } catch (Exception e) {
                //抛出导致失败的异常，调用方会收到ExecutionExceptio异常
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    /**
     * getPriceSync2与getPriceSync1是等价的。
     * 这里提供的并非阻塞式的代码，为了获取结果需要调用Future.get方法或CompletableFuture.join
     * @param product
     * @return
     */
    public Future<Double> getPriceSync2(String product) {
        //由工厂方法创建，并交由默认的ForkJoinPool来执行
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        //可能需要查询数据库来获取商品的价格，也可能需要联系外部服务获取折扣
        delay();
        //如果每次获取商品的价格都要等待1秒，这让人无法接收
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void randomDelay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //--------------------------

    public String getPriceWithDiscount(String product) {
        String threadName = Thread.currentThread().getName();

        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];

        System.out.println("getPriceWithDiscount threadName = " + threadName + ", price = " + price + ", code = " + code);

        return String.format("%s:%.2f:%s", name, price, code);
    }

    public String getPriceWithRandomDelay(String product) {
        String threadName = Thread.currentThread().getName();

        randomDelay();

        double price = random.nextDouble() * product.charAt(0) + product.charAt(1);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];

        System.out.println("getPriceWithDiscount threadName = " + threadName + ", price = " + price + ", code = " + code);

        return String.format("%s:%.2f:%s", name, price, code);
    }
}
