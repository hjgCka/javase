package com.hjg.plain.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

/**
 * 需要对多个商店查询某件商品价格，其中获取商品的方法和查询折扣的方法都是阻塞的。
 * 需要快速响应，而不是等待所有商品都查询出来才给出全部的响应。
 */
public class ResponseFutureCompleteApp {

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

        //map是中间操作，如果只到map这里为止，实际上并不会打印出字符串。
        CompletableFuture[] futures = findPricesStream("myPhone")
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);

        //这个方法等待所有任务执行完才会返回
        //CompletableFuture.allOf(futures).join();

        CompletableFuture.allOf(futures).whenComplete((result, ex) -> {
            //CompletableFuture.allOf返回的是CompletableFuture<Void>
            System.out.println("result = " + result + ", ex = " + ex);
        }).join();

        System.out.println("All shops have responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPriceWithRandomDelay(product), executor
                ))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                            CompletableFuture.supplyAsync(
                                    () -> Discount.applyDiscount(quote), executor
                            )
                        ));
    }
}
