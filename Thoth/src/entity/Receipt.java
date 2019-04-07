package entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @Column(name = "ReceiptId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int receiptId;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "ObjectId")
    private int objectId;

    @Column(name = "TotalValue")
    private BigDecimal totalValue;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "UserId")
    private int userId;

    @Column(name = "Date")
    private Date date;

    public Receipt(){

    }

    public Receipt(int objectId, BigDecimal totalValue, int userId, Date date) {
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

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
