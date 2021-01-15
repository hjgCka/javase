package com.hjg.jackson.example.util;

import com.hjg.jackson.example.model.Book;

import java.io.*;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
public class CloneUtil implements Cloneable, Serializable {

    public static Object clone(Book originalBook) throws IOException, ClassNotFoundException {
        try (
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bout);
                ) {

            out.writeObject(originalBook);

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
            return in.readObject();
        }
    }

}
