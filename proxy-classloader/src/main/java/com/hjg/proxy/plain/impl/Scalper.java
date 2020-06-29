package com.hjg.proxy.plain.impl;

import com.hjg.proxy.job.Worker;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Scalper implements Worker {

    private Worker worker;

    @Override
    public void sell() {
        System.out.println("不用排队!!!");
        worker.sell();
    }
}
