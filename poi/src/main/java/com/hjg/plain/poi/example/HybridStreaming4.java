package com.hjg.plain.poi.example;


import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheet;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This demonstrates how a hybrid approach to workbook read can be taken, using
 * a mix of traditional XSSF and streaming one particular worksheet (perhaps one
 * which is too big for the ordinary DOM parse).
 */
public class HybridStreaming4 {

    private static final String SHEET_TO_STREAM = "large sheet";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, OpenXML4JException {
        try (InputStream sourceBytes = HybridStreaming4.class.getClassLoader().getResourceAsStream("workbook.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(sourceBytes) {
                /**
                 * Avoid DOM parse of large sheet
                 */
                @Override
                public void parseSheet(java.util.Map<String, XSSFSheet> shIdMap, CTSheet ctSheet) {
                    if (!SHEET_TO_STREAM.equals(ctSheet.getName())) {
                        super.parseSheet(shIdMap, ctSheet);
                    }
                }
            };

            // Having avoided a DOM-based parse of the sheet, we can stream it instead.
            // 上面得workbook创建时，忽略了large sheet，无法用DOM解析。
            // 这里的代码，专门处理未被DOM解析的sheet。
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(workbook.getPackage());
            ContentHandler handler = new XSSFSheetXMLHandler(workbook.getStylesSource(), strings, createSheetContentsHandler(), false);

            XSSFReader xssfReader = new XSSFReader(workbook.getPackage());
            InputStream stream = xssfReader.getSheet("rId1");

            InputSource sheetSource = new InputSource(stream);

            XMLReader sheetParser = XMLHelper.newXMLReader();
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);

            workbook.close();
        }
    }

    private static SheetContentsHandler createSheetContentsHandler() {
        return new SheetContentsHandler() {

            @Override
            public void startRow(int rowNum) {
                System.out.println(rowNum);
            }

            @Override
            public void endRow(int rowNum) {
                System.out.println(rowNum);
            }

            @Override
            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                System.out.println(cellReference + formattedValue);
            }
        };
    }
}
