package com.hjg.reflect.service.impl;

import com.hjg.reflect.service.BookService;
import com.hjg.reflect.service.annn.Ccka;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/5
 */
@Ccka(name = "Jack", age = 22)
public class BookServiceImpl implements BookService {

    @Override
    public void printBook(String name) {
        System.out.println(name);
    }
}
