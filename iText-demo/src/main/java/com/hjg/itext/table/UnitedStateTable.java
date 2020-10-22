package com.hjg.itext.table;

import com.hjg.itext.util.MyResourceUtil;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/11
 */
public class UnitedStateTable {

    private static final Logger logger = LoggerFactory.getLogger(UnitedStateTable.class);

    private static final String DEST = "e:/itext_output/united_states.pdf";
    private static final String DATA = "united_states.csv";

    public static void main(String[] args) {
        try {
            PdfWriter pdfWriter = new PdfWriter(DEST);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            Document document = new Document(pdfDocument, PageSize.A4.rotate());
            document.setMargins(20, 20, 20, 20);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
            table.setWidth(UnitValue.createPercentValue(100));

            String fileName = MyResourceUtil.getFileName(DATA);
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            process(table, line, bold, true);

            while((line=br.readLine()) != null) {
                process(table, line, font, false);
            }
            br.close();

            document.add(table);
            document.close();
        } catch (Exception e) {
            logger.error("创建pdf时异常", e);
        }
    }

    private static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while(tokenizer.hasMoreTokens()) {
            if(isHeader) {
                table.addHeaderCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)
                        )
                );
            } else {
                table.addCell(
                        new Cell().add(
                                new Paragraph(tokenizer.nextToken()).setFont(font)
                        )
                );
            }
        }
    }
}
