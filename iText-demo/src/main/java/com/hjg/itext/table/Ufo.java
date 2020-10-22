package com.hjg.itext.table;

import com.hjg.itext.util.MyResourceUtil;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/10/12
 */
public class Ufo {

    private static final Logger logger = LoggerFactory.getLogger(Ufo.class);

    private static final String DEST = "e:/itext_output/ufo.pdf";
    private static final String DATA = "ufo.csv";

    static PdfFont helvetica = null;
    static PdfFont helveticaBold = null;

    public static void main(String[] args) {

        try {
            helvetica = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            helveticaBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            createPdf();
        } catch (Exception e) {
            logger.error("创建pdf时异常", e);
        }
    }

    private static void createPdf() throws Exception{
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST));
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());
        Document document = new Document(pdfDocument);

        Paragraph paragraph = new Paragraph("List of reported UFO sightings in 20th century")
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(helveticaBold).setFontSize(14);
        document.add(paragraph);

        Table table = new Table(new float[]{3, 5, 7, 4});
        table.setWidth(UnitValue.createPercentValue(100));

        String fileName = MyResourceUtil.getFileName(DATA);
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        process(table, line, helveticaBold, true);

        while((line=br.readLine()) != null) {
            process(table, line, helvetica, false);
        }
        br.close();

        document.add(table);
        document.close();
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

class MyEventHandler implements IEventHandler {

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDocument = docEvent.getDocument();

        PdfPage pdfPage = docEvent.getPage();
        int pageNumber = pdfDocument.getPageNumber(pdfPage);

        Rectangle pageSize = pdfPage.getPageSize();

        PdfCanvas pdfCanvas = new PdfCanvas(pdfPage.newContentStreamBefore(),
                pdfPage.getResources(), pdfDocument);

        //set background
        Color limeColor = new DeviceCmyk(0.208f, 0, 0.584f, 0);
        Color blueColor = new DeviceCmyk(0.445f, 0.0546f, 0, 0.0667f);

        pdfCanvas.saveState()
                .setFillColor(pageNumber % 2 == 1 ? limeColor : blueColor)
                .rectangle(pageSize.getLeft(), pageSize.getBottom(),
                        pageSize.getWidth(), pageSize.getHeight())
                .fill().restoreState();

        //add header and footer
        pdfCanvas.beginText()
                .setFontAndSize(Ufo.helvetica, 9)
                .moveText(pageSize.getWidth() / 2 - 60, pageSize.getTop() - 20)
                .showText("THE TRUTH IS OUT THERE")
                .moveText(60, -pageSize.getTop() + 30)
                .showText(String.valueOf(pageNumber))
                .endText();

        //add watermark
        Canvas canvas = new Canvas(pdfCanvas, pdfPage.getPageSize());
        canvas.setFontColor(ColorConstants.WHITE);
        canvas.setProperty(Property.FONT_SIZE, UnitValue.createPointValue(60));
        canvas.setProperty(Property.FONT, Ufo.helveticaBold);
        canvas.showTextAligned(new Paragraph("CONFIDENTIAL"),
                298, 421, pdfDocument.getPageNumber(pdfPage),
                TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        pdfCanvas.release();
    }
}




