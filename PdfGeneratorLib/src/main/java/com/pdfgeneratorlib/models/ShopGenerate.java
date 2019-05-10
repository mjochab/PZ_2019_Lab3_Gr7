package com.pdfgeneratorlib.models;

import java.math.BigDecimal;

public class ShopGenerate {

    private String zipCode;
    private String city;
    private String street;
    private BigDecimal profit;

    public ShopGenerate(ShopGenerate sdf) {
        this.zipCode = sdf.zipCode;
        this.city = sdf.city;
        this.street = sdf.street;
        this.profit = sdf.profit;
    }

    public ShopGenerate(String zipCode, String city, String street, BigDecimal profit) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.profit = profit;
    }

    public void wypisz(){
        System.out.println(zipCode+", "+city+", "+street+", "+profit);
    }

    public ShopGenerate kopia(){
        return new ShopGenerate(this);
    }
}
