package com.pdfgeneratorlib;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.*;

public class Main {

//    public static final String DEST = "PdfGeneratorLib/results/test/test.pdf";

    public static void main(String[] args) throws IOException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new Main().createPdf(DEST);

        InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream("shops.object");
            objIs = new ObjectInputStream(fileIs);
            RaportModel emp = (RaportModel) objIs.readObject();
            System.out.println(emp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){

            }
        }
    }

    public void createPdf(RaportModel data,String destination) throws IOException {


        FileOutputStream fos = new FileOutputStream(destination);
        PdfWriter writer = new PdfWriter(fos);

        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);

        document.add(new Paragraph("hello world"));

        Table table = new Table(8);
        for (int i = 0; i < 16; i++) {
            table.addCell("1");
            table.addFooterCell("2");
            //table.addCell("2");
        }
        document.add(table);

        document.close();
    }

}