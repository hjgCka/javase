package com.hjg.itext.font;

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
public class Chinese_text {

    private static final Logger logger = LoggerFactory.getLogger(Chinese_text.class);

    private static final String DEST = "e:/itext_output/cn_text.pdf";

    //使用ttc的字体文件时，需要在末尾加上',1'。查看FontProgramFactory的createFont方法。
    public static final String FONT = "font/songti-changgui.ttc,1";

    public static void main(String[] args) {
        String str = "你好, Spring";

        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
            Document document = new Document(pdfDocument);

            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
            Text text = new Text(str).setFont(font);
            Paragraph paragraph = new Paragraph();
            paragraph.add(text);

            document.add(paragraph);

            document.add(new Paragraph().setFont(font).add("你好, Java").setBold());

            document.close();

        } catch (Exception e) {
            logger.error("生成pdf时异常", e);
        }
    }
}
