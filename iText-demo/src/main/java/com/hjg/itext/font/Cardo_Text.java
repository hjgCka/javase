package com.hjg.itext.font;

import com.hjg.itext.util.ITextResourceUtil;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
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
 * @createdOn: 2021/2/19
 */
public class Cardo_Text {

    private static final Logger logger = LoggerFactory.getLogger(Cardo_Text.class);

    private static final String DEST = "e:/itext_output/cardo_text.pdf";

    public static final String REGULAR = ITextResourceUtil.getAbsoluteFilePath("font/Cardo-Regular.ttf");
    public static final String BOLD = ITextResourceUtil.getAbsoluteFilePath("font/Cardo-Bold.ttf");
    public static final String ITALIC = ITextResourceUtil.getAbsoluteFilePath("font/Cardo-Italic.ttf");

    public static void main(String[] args) {

        System.out.println(REGULAR);
        System.out.println(BOLD);
        System.out.println(ITALIC);

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
            Document document = new Document(pdfDocument);

            FontProgram fontProgram = FontProgramFactory.createFont(REGULAR);
            PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);

            PdfFont bold = PdfFontFactory.createFont(BOLD, true);
            PdfFont italic = PdfFontFactory.createFont(ITALIC, true);

            Text title =
                    new Text("The Strange Case of Dr. Jekyll and Mr. Hyde").setFont(bold);
            Text author = new Text("Robert Louis Stevenson").setFont(font);
            Paragraph p = new Paragraph().setFont(italic)
                    .add(title).add(" by ").add(author);

            document.add(p);
            document.close();
        } catch (Exception e) {
            logger.error("生成pdf时异常", e);
        }

    }
}
