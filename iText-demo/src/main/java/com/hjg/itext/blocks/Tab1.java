package com.hjg.itext.blocks;

import com.hjg.itext.util.ITextResourceUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/20
 */
public class Tab1 {

    private static final Logger logger = LoggerFactory.getLogger(Tab1.class);

    private static final String DEST = "e:/itext_output/tab1.pdf";

    private static final String SRC = "jekyll_hyde.csv";

    public static void main(String[] args) {

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
            Document document = new Document(pdfDocument, PageSize.A4.rotate());

            PdfCanvas pdfCanvas = new PdfCanvas(pdfDocument.addNewPage());
            for (int i = 1; i <= 10; i++) {
                pdfCanvas.moveTo(document.getLeftMargin() + i * 50, 0);
                pdfCanvas.lineTo(document.getLeftMargin() + i * 50, 595);
            }
            pdfCanvas.stroke();

            List<List<String>> resultSet = ITextResourceUtil.convert(ITextResourceUtil.getAbsoluteFilePath(SRC), "|");
            for (List<String> record : resultSet) {
                Paragraph p = new Paragraph();
                p.add(record.get(0).trim()).add(new Tab())
                        .add(record.get(1).trim()).add(new Tab())
                        .add(record.get(2).trim()).add(new Tab())
                        .add(record.get(3).trim()).add(new Tab())
                        .add(record.get(4).trim()).add(new Tab())
                        .add(record.get(5).trim());
                document.add(p);
            }

            document.close();
        } catch (Exception e) {
            logger.error("生成pdf时异常", e);
        }
    }
}
