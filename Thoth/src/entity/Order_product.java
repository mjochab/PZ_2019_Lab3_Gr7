package entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_product")
public class Order_product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product productId;

    @Column(name = "Amount")
    private int amount;

    public Order_product(){

    }

    public Order_product(Order orderId, Product productId, int amount) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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
