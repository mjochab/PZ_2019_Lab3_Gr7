package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @Column(name = "ReceiptId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receiptId;

    @ManyToOne
    @JoinColumn(name = "ShopId",nullable = false)
    private Shop shopId;

    @Column(name = "TotalValue",nullable = false)
    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    private User userId;

    @Column(name = "Date",nullable = false)
    private Date date;

    public Receipt(){

    }

    public Receipt(Shop shopId, BigDecimal totalValue, User userId, Date date) {
        this.shopId = shopId;
        this.totalValue = totalValue;
        this.userId = userId;
        this.date = date;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
