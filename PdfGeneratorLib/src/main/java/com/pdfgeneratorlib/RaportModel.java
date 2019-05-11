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
}

