package com.hjg.plain.sax;

import com.hjg.plain.sax.model.User;
import lombok.Data;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/17
 */
@Data
public class UserHandler extends DefaultHandler {

    private List<User> userList;
    private User user;
    private String tagName;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        userList = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    /**
     *
     * @param uri            命名空间的uri
     * @param localName      标签的名称
     * @param qName          带命名空间的标签名称
     * @param attributes     属性
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        String id = attributes.getValue("id");

        System.out.println("startElement : uri = " + uri+", localName = "+localName + ", qName = " + qName + ", id = " + id);

        if("user".equals(qName)) {
            this.user = new User();
            this.user.setId(Integer.valueOf(id));
        }

        this.tagName = qName;
    }

    /**
     *
     * @param uri            命名空间的uri
     * @param localName      标签的名称
     * @param qName          带命名空间的标签名称
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        System.out.println("endElement : uri = " + uri+", localName = "+localName + ", qName = " + qName);

        if(qName.equals("user")) {
            userList.add(this.user);
        }
        this.tagName = null;
    }

    /**
     * 处理在XML文件中读到的内容
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        String value = new String(ch, start, length);
        System.out.println("characters : value = " + value);

        if("name".equals(this.tagName)) {
            this.user.setName(value);
        } else if("password".equals(this.tagName)) {
            this.user.setPassword(value);
        }
    }
}
