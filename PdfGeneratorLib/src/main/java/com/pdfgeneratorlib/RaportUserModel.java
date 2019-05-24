package com.pdfgeneratorlib;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Klasa tworzaca model raportu z pracownikami
 *
 * @author Wojciech Gałka
 * @author Kamil Bania
 * @author Adrian Gajewski
 * @author Paweł Durda
 * @author Mateusz Gawlak
 * @see Serializable Serializacja to wbudowany mechanizm zapisywania obiektów, który pozwala na binarny zapis całego drzewa obiektów
 * @since 15 maj 2019
 */

public class RaportUserModel implements Serializable {

    private Integer userId;
    private BigDecimal total;

    /**
     * @param userId identyfikator pracownika
     * @param total  wartość towarów sprzedanych przez pracownika
     */

    public RaportUserModel(Integer userId, BigDecimal total) {
        this.userId = userId;
        if (total == null) {
            this.total = new BigDecimal(0);
        } else {
            this.total = total;
        }
    }

    public String getUserId() {
        return userId.toString();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTotal() {
        return total.toString();
    }

    public void setTotal(BigDecimal total) {
        if (total == null) {
            this.total = new BigDecimal(0);
        } else {
            this.total = total;
        }
    }

    /**
     * metoda zwraca model raportu z pracownikami w postaci stringa
     *
     * @return zwraca ciąg znaków zawierający Id użytkownika oraz wartość sprzedanych towarów przechowywaną w polu total
     */

    @Override
    public String toString() {
        return "RaportUserModel{" +
                "userId=" + userId +
                ", total=" + total +
                '}';
    }
}
