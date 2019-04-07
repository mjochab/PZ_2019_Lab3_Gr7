package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_receipt")
public class Product_receipt {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "ProductId")
    private int productId;

    @ManyToOne(targetEntity = Receipt.class)
    @JoinColumn(name = "ReceiptId")
    private int receiptId;

    @Column(name = "Amount")
    private int amount;

    @Column(name = "Price")
    private BigDecimal price;

    public Product_receipt(){

    }

    public Product_receipt(int productId, int receiptId, int amount, BigDecimal price) {
        this.productId = productId;
        this.receiptId = receiptId;
        this.amount = amount;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
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
                "productId=" + productId +
                ", receiptId=" + receiptId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
