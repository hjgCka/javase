package com.hjg.reflect;

import com.hjg.reflect.service.BookService;
import com.hjg.reflect.service.impl.BookServiceImpl;
import com.hjg.reflect.service.impl.MagezineServiceImpl;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/5
 */
public class TestReflect {

    @Test
    public void testReflect1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class cls = BookService.class;
        //getMethods()方法，返回的是public方法
        Arrays.stream(cls.getDeclaredMethods()).forEach(method -> {
            System.out.println(method.getName());
        });

        Method method = cls.getMethod("printBook", String.class);

        BookServiceImpl bookService = new BookServiceImpl();
        method.invoke(bookService, "Java");
    }

    /**
     * @Inherited，接口的实现类，无法获得接口的注解。
     * 在类的继承层次中，注解的信息可以传递。
     */
    @Test
    public void testAnnotationReflect() {
        this.printClassAnnotation(BookService.class);

        this.printClassAnnotation(BookServiceImpl.class);

        this.printClassAnnotation(MagezineServiceImpl.class);
    }

    private void printClassAnnotation(Class cls) {
        //获取类上的所有的公共注解
        Arrays.stream(cls.getDeclaredAnnotations()).forEach(annotation -> {
            System.out.println(annotation);
        });
    }

    private void printMethodAnnotation(Class cls) {
        Method[] methods = cls.getDeclaredMethods();

    }
}
