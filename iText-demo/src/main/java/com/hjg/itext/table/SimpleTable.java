package com.hjg.itext.table;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/11
 */
public class SimpleTable {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTable.class);

    public static final String DEST = "e:/itext_output/simple_table.pdf";

    public static void main(String[] args) {
        try {
            manipulatePdf(DEST);
        } catch (Exception e) {
            logger.error("创建pdf时失败", e);
        }
    }

    private static void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDocument);

        Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();

        for(int i=0; i<16; i++) {
            table.addCell("hi");
        }

        doc.add(table);
        doc.close();
    }
}
