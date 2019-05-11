package com.pdfgeneratorlib;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.*;
import java.util.List;

public class CreatePDF {

    public static void createPdf(List<RaportModel> data, String destination) throws IOException {

        //tworzenie pliku w którym będzie zapisany dokument
        FileOutputStream fos = new FileOutputStream(destination);

        //tworzenie obiektu zapisującego
        PdfWriter writer = new PdfWriter(fos);

        //utworzenie nowego dokumentu PDF
        PdfDocument pdf = new PdfDocument(writer);

        //utworzenie pustego dokumentu
        Document document = new Document(pdf);

        //utworzenie nagłówka dokumentu
        document.add(new Paragraph("THOTH raport sprzedażowy."));

        if (!data.isEmpty()) {
            Table table = new Table(1);
            for (RaportModel shop : data) {
                Cell shopAdress = new Cell();
                Cell productsRaport = new Cell();
                Cell usersRaport = new Cell();

                shopAdress.add(shop.getStreet() + " " + shop.getZipCode() + " " + shop.getCity() + " Zysk:" + shop.getProfit());         // Adding content to the cell
                table.addCell(shopAdress);      // Adding cell to the table


                if(!shop.getProducts().isEmpty()) {
                    Table productsTable = new Table(3);
                    for (RaportProductModel product : shop.getProducts()) {

                        productsTable.addCell(" "+product.getName());
                        productsTable.addCell(" "+product.getSold().toString());
                        productsTable.addCell(" "+product.getTotal_price().toString());
                    }
                    table.addCell(productsTable);
                }

                table.addCell("raporcik");

//                if(!shop.getUsers().isEmpty()) {
//                    Table userTable = new Table(2);
//                    for (RaportUserModel user : shop.getUsers()) {
//                        userTable.addCell(" "+user.getUserId().toString());
//                        userTable.addCell(" "+user.getTotal().toString());
//                    }
//                    table.addCell(userTable);
//                }
            }
            document.add(table);
        }

        document.close();
    }

}