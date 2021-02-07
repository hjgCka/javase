package com.hjg.proxy.cglib.service;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class CarMethodInterceptor implements MethodInterceptor {

    /**
     *
     * @param proxy               代理类对象(com.hjg.proxy.cglib.service.Cars$$EnhancerByCGLIB$$9d65f7da)
     * @param method             被代理的方法名称
     * @param objects            参数列表
     * @param methodProxy        CGLIB产生的代理类的对方法的代理对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("代理的方法名称：" + method.getName());
        System.out.println("代理类的方法代理对象：签名=" + methodProxy.getSignature());
        System.out.println("对象proxy=" + proxy.getClass().getName());

        Object result = methodProxy.invokeSuper(proxy, objects);

        System.out.println("方法代理调用的结果 = " + result);

        return result;
    }
}
