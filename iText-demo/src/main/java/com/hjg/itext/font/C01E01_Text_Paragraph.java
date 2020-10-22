package com.hjg.itext.font;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/12
 */
public class C01E01_Text_Paragraph {

    private static final Logger logger = LoggerFactory.getLogger(C01E01_Text_Paragraph.class);

    private static final String DEST = "e:/itext_output/text_paragraph.pdf";

    public static void main(String[] args) {
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
            Document document = new Document(pdfDocument);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

            Text title = new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
            Text author = new Text("Robert Louis Stevenson").setFont(font);
            
            Paragraph p = new Paragraph().add(title).add("by").add(author);

            document.add(p);

            document.close();

        } catch (Exception e) {
            logger.error("创建pdf时异常", e);
        }
    }
}
