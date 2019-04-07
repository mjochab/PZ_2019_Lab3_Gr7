package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "a_order")
public class Order {

    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "ObjectId_need", referencedColumnName = "ObjectId",nullable = false)
    private Shop objectId_need;

    @ManyToOne
    @JoinColumn(name = "ObjectId_delivery", referencedColumnName = "ObjectId",nullable = false)
    private Shop objectId_delivery;

    @ManyToOne
    @JoinColumn(name = "CustomerId",nullable = false)
    private Customer customerId;

    @Column(name = "DateOfOrder",nullable = false)
    private Date dateOfOrder;

    @ManyToOne
    @JoinColumn(name = "ParentId",referencedColumnName = "OrderId")
    private Order parentId;


    public Order(){

    }

    public Order(Shop objectId_need, Shop objectId_delivery, Customer customerId, Date dateOfOrder, Order parentId) {
        this.objectId_need = objectId_need;
        this.objectId_delivery = objectId_delivery;
        this.customerId = customerId;
        this.dateOfOrder = dateOfOrder;
        this.parentId = parentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Shop getObjectId_need() {
        return objectId_need;
    }

    public void setObjectId_need(Shop objectId_need) {
        this.objectId_need = objectId_need;
    }

    public Shop getObjectId_delivery() {
        return objectId_delivery;
    }

    public void setObjectId_delivery(Shop objectId_delivery) {
        this.objectId_delivery = objectId_delivery;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", objectId_need=" + objectId_need +
                ", objectId_delivery=" + objectId_delivery +
                ", customerId=" + customerId +
                ", dateOfOrder=" + dateOfOrder +
                ", parentId=" + parentId +
                '}';
    }
}
