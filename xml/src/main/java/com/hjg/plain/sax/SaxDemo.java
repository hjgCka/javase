package com.hjg.plain.sax;

import com.hjg.plain.sax.model.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/17
 */
public class SaxDemo {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        UserHandler userHandler = new UserHandler();

        try (InputStream is = SaxDemo.class.getClassLoader().getResourceAsStream("users.xml")) {
            saxParser.parse(is, userHandler);

            List<User> list = userHandler.getUserList();
            System.out.println(list);
        }

    }
}
