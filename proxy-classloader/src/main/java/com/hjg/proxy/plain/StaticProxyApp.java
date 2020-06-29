package com.hjg.proxy.plain;

import com.hjg.proxy.job.Worker;
import com.hjg.proxy.plain.impl.Scalper;
import com.hjg.proxy.plain.impl.Seller;

/**
 * 实现静态代理。
 */
public class StaticProxyApp {

    public static void main(String[] args) {
        for(int i=0; i<args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }


        Worker worker = new Seller();
        worker.sell();

        System.out.println("===========================");

        Worker proxy = new Scalper(worker);
        proxy.sell();
    }
}
