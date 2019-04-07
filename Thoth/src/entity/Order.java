package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "ObjectId_need")
    private int objectId_need;

    public Order(){

    }

    public Order(int objectId_need) {
        this.objectId_need = objectId_need;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getObjectId_need() {
        return objectId_need;
    }

    public void setObjectId_need(int objectId_need) {
        this.objectId_need = objectId_need;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", objectId_need=" + objectId_need +
                '}';
    }
}
