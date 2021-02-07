package com.hjg.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class WorkerHandler implements InvocationHandler {

    private Object target;

    public WorkerHandler(Object target) {
        this.target = target;
    }

    /**
     * 该方法被JDK产生的代理类对象所调用。
     * @param proxy      JDK产生的代理类对象。
     * @param method     代理的方法。
     * @param args       方法的产生。
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        System.out.println("调用方法名称=" + methodName);

        if(args == null) {
            System.out.println("调用方法参数==null");
        } else {
            for(int i=0; i<args.length; i++) {
                System.out.println("调用方法参数，args[i]=" + args[i]);
            }
        }

        return method.invoke(target, args);
    }
}
