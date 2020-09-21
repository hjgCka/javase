package com.hjg.plain.poi;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/16
 */
public class TextExtractionApp {

    public void processSheet(InputStream is) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        OPCPackage pkg = OPCPackage.open(is);
        XSSFReader xssfReader = new XSSFReader( pkg );

        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        InputStream sheet = xssfReader.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet);

        parser.parse(sheetSource);
        sheet.close();
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        XMLReader parser = XMLHelper.newXMLReader();
        ContentHandler handler = new TextExtractionApp.SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    private static class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;
        //标签的内容
        private String cellValue;

        private List<String> phoneList = new ArrayList<>();
        private boolean nextIsPhone = false;

        //从0开始，每次+1
        private int batchId = 0;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        @Override
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {

            // c => cell
            if(name.equals("c")) {
                String referenceId = attributes.getValue("r");
                if(referenceId.startsWith("B")) {
                    //只获取第二列的数据，这一列是手机号
                    nextIsPhone = true;
                } else {
                    nextIsPhone = false;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            if(nextIsPhone) {
                phoneList.add(cellValue);
                nextIsPhone = false;
            }

            if(phoneList.size() == 1000) {
                //同时传递batchId，如果为1，则需要从下标1开始访问
                batchId++;
                System.out.println("获得1000个手机号，进行一批处理。batchId = " + batchId + ", [0] = " + phoneList.get(0));
                phoneList.clear();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            cellValue = new String(ch, start, length);
        }

        @Override
        public void endDocument() {
            if(phoneList.size() > 0) {
                batchId++;
                System.out.println("获得剩余手机号，进行最后一批处理。batchId = " + batchId + ", size = " + phoneList.size());
                phoneList.clear();
            }
        }
    }

    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();

            String name = "data.xlsx";
            InputStream is = TextExtractionApp.class.getClassLoader().getResourceAsStream(name);

            TextExtractionApp textExtractionApp = new TextExtractionApp();
            textExtractionApp.processSheet(is);

            long span = System.currentTimeMillis() - startTime;
            System.out.println("span = " + span);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
