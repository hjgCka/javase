package com.hjg.collections.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 与其他几种引用都不同，虚引用并不会决定对象的生命周期。
 * 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 *
 * @description:
 * @author: hjg
 * @createdOn: 2021/3/8
 */
public class WeakRef {

    public static void main(String[] args) {

        int _1m = 1024 * 1024;

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        Thread thread = new Thread(() -> {
            int count = 0;
            try {
                while(true) {
                    WeakReference<String> ele = (WeakReference) referenceQueue.remove();
                    System.out.println((count++) + "回收了 " + ele);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Object value = new Object();
        Map<Object, Object> map = new HashMap<>();
        for(int i=0; i<10_000; i++) {
            byte[] bytes = new byte[_1m];

            //当weakReference不再被引用时，相应的数据被回收
            //而且当bytes被gc时，其对应的包装类weakReference，就会被放入队列
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, referenceQueue);
            System.out.println(i + "分配了1M");

            //如果注释这一行，referenceQueue一直为空，但是不会发生OOM。
            map.put(weakReference, value);
        }
        //确实放入了10_000个元素，但是key的对象的byte[]被回收了
        System.out.println("map size = " + map.size());

    }
}
