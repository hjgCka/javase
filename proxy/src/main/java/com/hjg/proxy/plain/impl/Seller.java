package com.hjg.proxy.plain.impl;

import com.hjg.proxy.job.Worker;

public class Seller implements Worker {

    @Override
    public void sell() {
        System.out.println("成功把票卖出去了!");
    }
}
