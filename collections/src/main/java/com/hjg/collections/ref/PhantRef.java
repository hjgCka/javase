package com.hjg.collections.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Date;

/**
 *
 * PhantomReference仅仅用于被垃圾回收时收到一个系统通知。
 * @description:
 * @author: hjg
 * @createdOn: 2021/3/8
 */
public class PhantRef {

    public static void main(String[] args) {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Date> phantomReference = new PhantomReference<>(new Date(), referenceQueue);

        //无法通过虚引用(幽灵引用)来取得实例
        Date date = phantomReference.get();
        System.out.println(date);

        //能触发gc，从referenceQueue中获得PhantomReference元素
        System.gc();

        new Thread(() -> {
            int count = 0;
            try {
                while(true) {
                    PhantomReference<Date> ele = (PhantomReference) referenceQueue.remove();
                    System.out.println((count++) + "回收了 " + ele);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
