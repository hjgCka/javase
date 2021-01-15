import com.hjg.jackson.example.model.Book;
import com.hjg.jackson.example.model.ExternalizableBook;
import com.hjg.jackson.example.model.ImmutableBook;
import com.hjg.jackson.example.util.CloneUtil;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/15
 */
public class JdkSerializeTest {

    @Test
    public void transientTest() throws IOException, ClassNotFoundException {
        Book book1 = new Book();
        book1.setName("Java");
        book1.setDate(new Date());
        book1.setPrice(new BigDecimal("5000"));

        Book book2 = new Book();
        book2.setName("C++");
        book2.setDate(new Date());
        book2.setPrice(new BigDecimal("20000"));

        System.out.println(book1);
        System.out.println(book2);

        String location = "e:/tmp/book.ser";
        Files.deleteIfExists(Paths.get(location));

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location));
        out.writeObject(book1);
        out.writeObject(book2);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(location));
        //按照写入顺序读取，且price为null
        Book b1 = (Book)in.readObject();
        Book b2 = (Book)in.readObject();
        in.close();

        System.out.println(b1);
        System.out.println(b2);
    }

    @Test
    public void externalizableBookTest() throws IOException, ClassNotFoundException {
        ExternalizableBook book = new ExternalizableBook("Spring", new Date(), new BigDecimal("200"));
        /*book.setPrice(new BigDecimal("100"));
        book.setName("Spring");
        book.setDate(new Date());*/
        System.out.println(book);

        String location = "e:/tmp/exter_book.ser";
        Files.deleteIfExists(Paths.get(location));

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location));
        out.writeObject(book);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(location));
        ExternalizableBook b1 = (ExternalizableBook) in.readObject();
        System.out.println(b1);
    }

    @Test
    public void constructorPropertyTest() throws IOException, ClassNotFoundException {
        ImmutableBook immutableBook = new ImmutableBook("C++", new Date(), new BigDecimal("2000"));
        System.out.println(immutableBook);

        String location = "e:/tmp/immutable_book.ser";
        Files.deleteIfExists(Paths.get(location));

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(location));
        out.writeObject(immutableBook);
        out.close();

        //在使用了@ConstructorProperties注解的情况下，即使没有无参构造函数，也能成功反序列化
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(location));
        ImmutableBook book = (ImmutableBook) in.readObject();
        in.close();

        System.out.println(book);
    }

    @Test
    public void deepCloneTest() throws IOException, ClassNotFoundException {
        Book book1 = new Book();
        book1.setName("Java");
        book1.setDate(new Date());
        book1.setPrice(new BigDecimal("5000"));
        System.out.println(book1);

        Book newBook = (Book) CloneUtil.clone(book1);
        System.out.println(newBook);
    }
}
