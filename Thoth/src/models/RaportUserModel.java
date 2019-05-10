package models;

import java.math.BigDecimal;

public class RaportUserModel {
    private Integer userId;
    private BigDecimal total;

    public RaportUserModel(Integer userId, BigDecimal total) {
        this.userId = userId;
        this.total = total;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotal() {
        return total;
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
