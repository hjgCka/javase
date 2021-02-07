package com.hjg.proxy.cglib.service;

import com.hjg.proxy.cglib.CglibUtil;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class ServiceTest {

    public static void main(String[] args) {
        Cars car = CglibUtil.createCarCglibProxy();

        //设置了过滤器之后，调用这个方法时，并未进行代理
        car.fix();
    }
}
