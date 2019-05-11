package com.pdfgeneratorlib;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RaportModel implements Serializable {
    private Integer shopId;
    private String street;
    private String zipCode;
    private String city;
    private BigDecimal profit;
    private List<RaportProductModel> products;
    private List<RaportUserModel> users;

    public RaportModel(Integer shopId, String street, String zipCode, String city, BigDecimal profit, RaportProductModel products, RaportUserModel users) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.profit = profit;
        this.products.add(products);
        this.users.add(users);
    }

    public RaportModel(Integer shopId, String street, String zipCode, String city, BigDecimal profit) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        if(profit == null){
            this.profit = new BigDecimal(0);
        }
        else {
            this.profit = profit;
        }
    }

    public RaportModel(Integer shopId, String street, String zipCode, String city) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public List<RaportProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<RaportProductModel> products) {
        this.products = products;
    }

    public List<RaportUserModel> getUsers() {
        return users;
    }

    public void setUsers(List<RaportUserModel> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RaportModel{" +
                "shopId=" + shopId +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", profit=" + profit +
                ", products=" + products +
                ", users=" + users +
                '}';
    }

    public void createPdf(List<RaportModel> data, String destination) throws IOException {

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

        for(RaportModel shop : data){
            Table table = new Table(1);
            Cell shopAdress = new Cell();
            Cell productsRaport = new Cell();
            Cell usersRaport = new Cell();

            shopAdress.add(shop.getStreet()+" "+shop.getZipCode()+" "+shop.getCity()+" Zysk:"+shop.getProfit());         // Adding content to the cell
            table.addCell(shopAdress);      // Adding cell to the table

            Table productsTable = new Table(3);
            for(RaportProductModel product : shop.getProducts()){

                productsTable.addCell(product.getName());
                productsTable.addCell(product.getSold().toString());
                productsTable.addCell(product.getTotal_price().toString());
            }
            table.addCell(productsTable);

            Table userTable = new Table(2);
            for(RaportUserModel user : shop.getUsers()){
                userTable.addCell(user.getUserId().toString());
                userTable.addCell(user.getTotal().toString());
            }
            table.addCell(userTable);
            document.add(table);
        }

        document.close();
    }
}

