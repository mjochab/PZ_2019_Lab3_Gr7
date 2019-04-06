package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_product")
public class Order_product {
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private int productId;

    @Column(name = "Amount")
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order_product{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
