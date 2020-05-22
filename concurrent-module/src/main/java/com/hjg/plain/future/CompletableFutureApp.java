package com.hjg.plain.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * 需要对多个商店查询某件商品价格，其中获取商品的方法是阻塞的。
 */
public class CompletableFutureApp {

    private static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"), new Shop("Jack"), new Shop("Jones"),
            new Shop("Geroge"), new Shop("John"), new Shop("Jojo"));

    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            //。Java程序无法终止或者退出一个正
            //在运行中的线程，所以最后剩下的那个线程会由于一直等待无法发生的事件而引发问题。
            //如果将线程标记为守护进程，意味着程序退出时它也会被回收。
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            });

    public static void main(String[] args) {
        long start = System.nanoTime();

        //findPrices1需要用4秒多，而findPrices3只需要用1秒多
        //很多时候findPrices2和findPrices3的用时差不多，但是findPrices3可以配置线程，
        //这是并行流版本所做不到的
        System.out.println(findPrices3("myPhone27S"));

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPrices1(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPrices2(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPrices3(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream().map(shop -> CompletableFuture.supplyAsync(() ->
                        shop.getName() + " Price is " + shop.getPrice(product),
                        executor)
        ).collect(Collectors.toList());

        //TODO 流操作的延迟特性是指什么？？

        //使用了2个不同的流水线。
        // 考虑到流操作之间的延迟特性，如果放在同一个流水线上发现不同商家的请求会以
        //顺序，同步执行的方式才会成功。
        // 每个CompletableFuture对象只能在前一个操作结束之后执行查询商家的动作。
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
