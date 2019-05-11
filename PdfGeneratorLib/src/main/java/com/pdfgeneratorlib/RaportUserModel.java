package com.pdfgeneratorlib;

import java.io.Serializable;
import java.math.BigDecimal;

public class RaportUserModel implements Serializable {
    private Integer userId;
    private BigDecimal total;

    public RaportUserModel(Integer userId, BigDecimal total) {
        this.userId = userId;
        this.total = total;
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
        this.total = total;
    }

    @Override
    public String toString() {
        return "RaportUserModel{" +
                "userId=" + userId +
                ", total=" + total +
                '}';
    }
}
