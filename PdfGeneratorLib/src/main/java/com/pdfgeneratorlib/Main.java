package com.pdfgeneratorlib;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static final String DEST = "PdfGeneratorLib/results/test/test.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Main().createPdf(DEST);
    }

    public void createPdf(String destination) throws IOException {

        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph("hello world"));

        document.close();
    }

}
