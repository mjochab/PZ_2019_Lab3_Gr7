package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_receipt")
public class Product_receipt {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ProductId",nullable = false)
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "ReceiptId",nullable = false)
    private Receipt receiptId;

    @Column(name = "Amount",nullable = false)
    private int amount;

    @Column(name = "Price",nullable = false)
    private BigDecimal price;

    public Product_receipt(){

    }

    public Product_receipt(Product productId, Receipt receiptId, int amount, BigDecimal price) {
        this.productId = productId;
        this.receiptId = receiptId;
        this.amount = amount;
//        mnożenie przez ilosc sztuk poniewaz przy generowaniu raportu uzywamy SUM
        this.price = price.multiply(new BigDecimal(amount));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Receipt getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipt receiptId) {
        this.receiptId = receiptId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product_receipt{" +
                "id=" + id +
                ", productId=" + productId +
                ", receiptId=" + receiptId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
