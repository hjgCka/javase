package com.hjg.proxy.cglib.service;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class Cars {
    @Override
    public String toString() {
        return "C----A-----R";
    }

    public void run() {
        String className = this.getClass().getName();
        System.out.println("className = " + className + ",  car running...");
        this.fix();
    }

    public String getName() {
        return "BENZ";
    }

    protected void fix() {
        System.out.println("fix car...");
    }

    private void sell() {
        System.out.println("sell car...");
    }

    void recycle() {
        System.out.println("car recycle");
    }
}
