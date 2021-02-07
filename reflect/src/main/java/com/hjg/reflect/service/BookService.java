package com.hjg.reflect.service;

import com.hjg.reflect.service.annn.Cc;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/5
 */
@Cc(age = 20)
public interface BookService {

    void printBook(String name);
}
