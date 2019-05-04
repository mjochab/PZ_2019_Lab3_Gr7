package models;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

public class SalesCreatorModel {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer discount;
    private Integer amount;

    public SalesCreatorModel() {
    }

    public SalesCreatorModel(Integer productId, String name, BigDecimal price, Integer discount, Integer amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
    }

    public SalesCreatorModel(Integer productId, String name, BigDecimal price, Integer discount, Long amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.amount = Integer.valueOf(amount.intValue());
    }

    public SalesCreatorModel(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}