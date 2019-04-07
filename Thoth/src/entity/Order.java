package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "ObjectId_need")
    private int objectId_need;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "CustomerId")
    private int customerId;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "ObjectId_delivery")
    private int objectId_delivery;

    @Column(name = "DateOfOrder")
    private Date dateOfOrder;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "ParentId",nullable = true)
    private int ParentId;
}
