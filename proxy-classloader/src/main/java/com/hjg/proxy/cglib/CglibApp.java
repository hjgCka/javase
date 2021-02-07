package com.hjg.proxy.cglib;

import com.hjg.proxy.cglib.service.Cars;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class CglibApp {

    public static void main(String[] args) {
        System.getProperties().setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "e:/cglib");

        Cars cars = CglibUtil.createCarCglibProxy();

        //在代理类上调用toString()方法，也会被拦截进入CarMethodInterceptor。
        //可以设置过滤器，对某些方法过滤，不再进行代理。
        System.out.println(cars.toString());

        //在这个例子中，方法内的互相调用，还是会进行代理。
        //因为，实例类只有一个，那就是CGLIB产生的代理类。方法调用在代理类上，就会进行方法代理。
        cars.run();

        //由于修饰符的限制而必须在相同包或子包才能调用，
        //但在能调用这2个方法的地方，进行调用时，实际也进行了代理。
        //cars.fix();
        //cars.recycle();
    }
}
