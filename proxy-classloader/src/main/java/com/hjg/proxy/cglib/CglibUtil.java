package com.hjg.proxy.cglib;

import com.hjg.proxy.cglib.service.CarMethodFilter;
import com.hjg.proxy.cglib.service.CarMethodInterceptor;
import com.hjg.proxy.cglib.service.Cars;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class CglibUtil {

    public static Cars createCarCglibProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Cars.class);

        //设置过滤器
        CarMethodFilter filter = new CarMethodFilter();
        enhancer.setCallbackFilter(filter);

        //索引对应过滤器返回的值
        Callback noProxy = NoOp.INSTANCE;
        Callback carMethodInterceptor = new CarMethodInterceptor();
        Callback[] array = new Callback[]{noProxy, carMethodInterceptor};
        enhancer.setCallbacks(array);

        return (Cars) enhancer.create();
    }
}
