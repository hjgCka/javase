package com.hjg.collections.ref;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 *
 * https://www.cnblogs.com/skywang12345/p/3311092.html
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-collections-maps-2/src/test/java/com/baeldung/map/weakhashmap/WeakHashMapUnitTest.java
 *
 * @description:
 * @author: hjg
 * @createdOn: 2021/3/8
 */
public class WeahMap {

    public static void main(String[] args) {
        Map<String, Person> map = new WeakHashMap<>();

        //直接使用字符串常量，会放入到方法区的常量池
        String k1 = new String("John");
        String k2 = new String("Jack");
        String k3 = new String("Jim");

        map.put(k1, new Person("John", 20));
        map.put(k2, new Person("Jack", 22));
        map.put(k3, new Person("Jim", 23));

        //如果k1, k2, k3都是强引用的话，那么WeakHashMap和普通的HashMap一样。

        System.out.println("size = " + map.size());

        k1 = null;
        System.gc();

        //gc之后，只有2个元素了
        Set<Map.Entry<String, Person>> set = map.entrySet();
        for(Map.Entry ele : set) {
            System.out.println(ele);
        }
    }
}

@Data
@AllArgsConstructor
class Person {
    String name;
    int age;
}


