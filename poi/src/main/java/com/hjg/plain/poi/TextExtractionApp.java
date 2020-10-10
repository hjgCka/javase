package com.hjg.plain.poi;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/16
 */
public class TextExtractionApp {

    private static final Logger logger = LoggerFactory.getLogger(TextExtractionApp.class);

    public void processSheet(InputStream is) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        OPCPackage pkg = OPCPackage.open(is);
        XSSFReader xssfReader = new XSSFReader( pkg );

        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        StylesTable stylesTable = xssfReader.getStylesTable();
        XMLReader parser = fetchSheetParser(sst, stylesTable);

        InputStream sheet = xssfReader.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet);

        parser.parse(sheetSource);
        sheet.close();
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst, StylesTable stylesTable) throws SAXException,
            ParserConfigurationException {
        XMLReader parser = XMLHelper.newXMLReader();
        ContentHandler handler = new TextExtractionApp.SheetHandler(sst, stylesTable);
        parser.setContentHandler(handler);
        return parser;
    }

    private static class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;
        private StylesTable stylesTable;
        //标签的内容
        private String cellValue;

        private List<RowModel> phoneList = new ArrayList<>();
        private RowModel tempRowModel;
        //1-userid , 2-phone, 3-accept_date, 4-start_date, 5-end_date
        private int fieldIndex = 0;

        private String cellStyleStr;
        private DataFormatter formatter = new DataFormatter();
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //从0开始，每次+1
        private int lineNumber = 0;
        private int batchId = 0;

        private SheetHandler(SharedStringsTable sst, StylesTable stylesTable) {
            this.sst = sst;
            this.stylesTable = stylesTable;
        }

        @Override
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {

            if(name.equals("row")) {
                tempRowModel = new RowModel();
                lineNumber++;
            }

            // c => cell
            if(name.equals("c")) {

                String referenceId = attributes.getValue("r");
                if(referenceId.startsWith("A")) {
                    fieldIndex = 1;
                } else if(referenceId.startsWith("B")) {
                    //只获取第二列的数据，这一列是手机号
                    fieldIndex = 2;
                } else if(referenceId.startsWith("C")) {
                    fieldIndex = 3;
                    cellStyleStr = attributes.getValue("s");
                } else if(referenceId.startsWith("D")) {
                    fieldIndex = 4;
                    cellStyleStr = attributes.getValue("s");
                } else if(referenceId.startsWith("E")) {
                    fieldIndex = 5;
                    cellStyleStr = attributes.getValue("s");
                } else {
                    fieldIndex = 0;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            if(name.equals("c")) {
                int idx;
                switch (fieldIndex) {
                    case 0:
                        break;
                    case 1:
                        idx = Integer.parseInt(cellValue);
                        tempRowModel.setUserId(sst.getItemAt(idx).getString());
                        break;
                    case 2:
                        tempRowModel.setPhone(cellValue);
                        break;
                    case 3:
                        if(lineNumber > 1) {
                            //表头不进行设置
                            tempRowModel.setAcceptDate(this.getDateCellContent());
                        }
                        break;
                    case 4:
                        if(lineNumber > 1) {
                            tempRowModel.setStartDate(this.getDateCellContent());
                        }
                        break;
                    case 5:
                        if(lineNumber > 1) {
                            tempRowModel.setEndDate(this.getDateCellContent());
                        }
                        break;
                    default:
                        break;
                }
            }

            if(name.equals("row")) {
                if(lineNumber > 1) {
                    //跳过表头
                    phoneList.add(tempRowModel);
                }
            }

            if(phoneList.size() == 1000) {
                //同时传递batchId，如果为1，则需要从下标1开始访问
                batchId++;
                logger.info("获得1000个手机号，进行一批处理。batchId = {}, [0] = {}", batchId, phoneList.get(0));
                phoneList.clear();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            cellValue = new String(ch, start, length);
        }

        @Override
        public void endDocument() {
            int length = phoneList.size();
            if(length > 0) {
                batchId++;
                logger.info("获得剩余手机号，进行最后一批处理。batchId = {}, last = {}", batchId, phoneList.get(length - 1));
                phoneList.clear();
            }
        }

        private String getDateCellContent() {
            Date date = DateUtil.getJavaDate(Double.parseDouble(cellValue));
            String str = sdf.format(date);

            /*int styleIndex = Integer.parseInt(this.cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);

            int formatIndex = style.getDataFormat();
            String dateFormat = style.getDataFormatString();

            String str = formatter.formatRawCellContents(Double.parseDouble(cellValue), formatIndex, dateFormat);*/

            return str;
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
            logger.info("span = {}", span);
        } catch (Exception e) {
            logger.error("解析文件错误", e);
        }
    }
}
