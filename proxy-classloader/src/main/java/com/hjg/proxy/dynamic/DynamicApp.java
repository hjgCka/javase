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

        //该方法使用了Class.forName有3个参数的重载方法来导入接口Class
        //Class.forName(p1, p2, p3)，p3指定的是类加载器，如果null则用bootstrp装载器。
        //Class.forName(p1)，默认使用调用者的类加载器
        ClassLoader mainCls = Thread.currentThread().getContextClassLoader();
        Worker proxy = (Worker)Proxy.newProxyInstance(mainCls, new Class[]{Worker.class}, workerHandler);

        proxy.sell();
    }
}
