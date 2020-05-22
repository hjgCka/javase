package com.hjg.plain.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * 需要对多个商店查询某件商品价格，其中获取商品的方法和查询折扣的方法都是阻塞的。
 */
public class MultiFutureApp {

    private static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));

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

        //findPrices1这种方式很慢，因为浪费了CPU时间。
        //所以这种情况下，用并行流改善有限，且它受到通用的线程池的影响
        //使用CompletableFuture以及自定义的线程池更好

        //findPrices1--用时8秒多，findPrices2只用时2秒多
        System.out.println(findPrices2("myPhone27S"));

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static List<String> findPrices1(String product) {
        return shops.stream()
                .map(shop -> shop.getPriceWithDiscount(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    private static List<String> findPrices2(String product) {

        //名称中不带Async的方法和它的前一个任务一样，在同一个线程中运行。(没有线程切换，都在当前线程运行)
        //而名称以Async结尾的方法会将后续的任务提交到一个线程池

        //thenCombine可以将2个不相干的CompletableFuture整合起来，无论是否存在依赖

        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPriceWithDiscount(product), executor
                ))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                            CompletableFuture.supplyAsync(
                                    () -> Discount.applyDiscount(quote), executor
                            )
                        ))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
