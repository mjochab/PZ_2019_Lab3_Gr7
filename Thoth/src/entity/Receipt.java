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
    @JoinColumn(name = "ObjectId",nullable = false)
    private Shop objectId;

    @Column(name = "TotalValue",nullable = false)
    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    private User userId;

    @Column(name = "Date",nullable = false)
    private Date date;

    public Receipt(){

    }

    public Receipt(Shop objectId, BigDecimal totalValue, User userId, Date date) {
        this.objectId = objectId;
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

    public Shop getObjectId() {
        return objectId;
    }

    public void setObjectId(Shop objectId) {
        this.objectId = objectId;
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

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", objectId=" + objectId +
                ", totalValue=" + totalValue +
                ", userId=" + userId +
                ", date=" + date +
                '}';
    }
}
