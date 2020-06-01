package com.hjg.proxy.dynamic;

import com.hjg.proxy.job.Worker;
import com.hjg.proxy.plain.impl.Seller;

import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.Properties;

public class DynamicApp {

    public static void main(String[] args) {

        Worker target = new Seller();
        WorkerHandler workerHandler = new WorkerHandler(target);

        ClassLoader cls = DynamicApp.class.getClassLoader();
        System.out.println(cls);

        //idea 可以查看命令
        Properties properties = System.getProperties();
        Enumeration names = properties.propertyNames();
        while(names.hasMoreElements()){
            String key = (String)names.nextElement();
            String value = properties.getProperty(key);
            System.out.println("key = " + key + ", value = " + value);
        }

        //该方法使用了Class.forName来导入接口Class
        //Class.forName()，loader指定装载参数类所用的类装载器，如果null则用bootstrp装载器。
        ClassLoader mainCls = Thread.currentThread().getContextClassLoader();
        Worker proxy = (Worker)Proxy.newProxyInstance(mainCls, new Class[]{Worker.class}, workerHandler);

        proxy.sell();
    }
}
