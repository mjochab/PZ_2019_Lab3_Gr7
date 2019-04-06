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

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private int objectId_need;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private int customerId;

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private int objectId_delivery;

    @Column(name = "DateOfOrder")
    private Date dateOfOrder;

    @ManyToOne
    @JoinColumn(name = "OrderId",nullable = true)
    private int ParentId;
}
