package com.hjg.itext.blocks;

import com.hjg.itext.util.ITextResourceUtil;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/20
 */
public class ImagePdf {

    private static final Logger logger = LoggerFactory.getLogger(ImagePdf.class);

    private static final String IMAGE = "image/tx.png";

    private static final String DEST = "e:/itext_output/image.pdf";

    public static void main(String[] args) {
        try {
            Files.deleteIfExists(Paths.get(DEST));
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
            Document document = new Document(pdfDocument);

            Paragraph p = new Paragraph(
                    "Mary Reilly is a maid in the household of Dr. Jekyll: ");
            document.add(p);
            logger.info("pWidth = {}, pMarginLeft={}", p.getWidth(), p.getMarginLeft());

            //左下角为坐标系原点
            Image img = new Image(ImageDataFactory.create(ITextResourceUtil.getAbsoluteFilePath(IMAGE)), 320, 750, 150);
            document.add(img);

            document.close();
        } catch (Exception e) {
            logger.error("产生pdf时异常", e);
        }
    }
}
