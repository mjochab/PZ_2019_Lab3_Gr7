package models;

import java.math.BigDecimal;

public class RaportModel {
    private String street;
    private String zipCode;
    private String city;
    private BigDecimal profit;
    private RaportProductModel[] Products;

    public RaportModel(String street, String zipCode, String city, BigDecimal profit, RaportProductModel[] products) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.profit = profit;
        Products = products;
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

    public RaportProductModel[] getProducts() {
        return Products;
    }

    public void setProducts(RaportProductModel[] products) {
        Products = products;
    }
}

