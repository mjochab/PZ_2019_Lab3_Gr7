package com.pdfgeneratorlib;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Klasa tworzaca model raportu z produktami
 *
 * @author Wojciech Gałka
 * @author Kamil Bania
 * @author Adrian Gajewski
 * @author Paweł Durda
 * @author Mateusz Gawlak
 * @since 15 maj 2019
 * @see Serializable Serializacja to wbudowany mechanizm zapisywania obiektów, który pozwala na binarny zapis całego drzewa obiektów
 */

public class RaportProductModel implements Serializable {

    private Integer shopId;
    private String name;
    private Long sold;
    private BigDecimal total_price;

    /**
     * @param shopId identyfikator sklepu
     * @param name nazwa
     * @param sold sprzedane
     * @param total_price zsumowane ceny
     */

    public RaportProductModel(Integer shopId, String name, Long sold, BigDecimal total_price) {
        this.shopId = shopId;
        this.name = name;
        this.sold = sold;
        this.total_price = total_price;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    /**
     *
     * @return metoda zwraca raport dotyczacy produktu w postaci stringa
     */
    @Override
    public String toString() {
        return "RaportProductModel{" +
                "shopId=" + shopId +
                ", name='" + name + '\'' +
                ", sold=" + sold +
                ", total_price=" + total_price +
                '}';
    }
}
