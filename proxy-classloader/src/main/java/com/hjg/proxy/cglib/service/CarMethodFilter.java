package com.hjg.proxy.cglib.service;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/7
 */
public class CarMethodFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        int modifier = method.getModifiers();
        System.out.println(Modifier.toString(modifier) + "  " + method.getName());

        //0-不进行代理，1-进行代理
        if(Modifier.isPublic(modifier)) {
            return 1;
        } else {
            return 1;
        }
    }
}
