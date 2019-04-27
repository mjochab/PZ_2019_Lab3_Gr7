package models;

import java.math.BigDecimal;

public class SalesDataModel {
    private String zipCode;
    private String city;
    private String street;
    private BigDecimal profit;

    public SalesDataModel() {
    }

    public SalesDataModel(String zipCode, String city, String street, BigDecimal profit) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.profit = profit;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
