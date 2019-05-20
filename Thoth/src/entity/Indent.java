package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "indent")
public class Indent {

    @Id
    @Column(name = "IndentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int indentId;

    @ManyToOne
    @JoinColumn(name = "ShopId_need", referencedColumnName = "ShopId")
    private Shop shopId_need;

    @ManyToOne
    @JoinColumn(name = "ShopId_delivery", referencedColumnName = "ShopId")
    private Shop shopId_delivery;

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customerId;

    @Column(name = "DateOfOrder",nullable = false)
    private Date dateOfOrder;

    @ManyToOne
    @JoinColumn(name = "ParentId",referencedColumnName = "IndentId")
    private Indent parentId;

    private Boolean isComplex;

    public Indent(){

    }

    public Indent(Shop shopId_need, Shop shopId_delivery, Customer customerId, Date dateOfOrder, Indent parentId, Boolean isComplex) {
        this.shopId_need = shopId_need;
        this.shopId_delivery = shopId_delivery;
        this.customerId = customerId;
        this.dateOfOrder = dateOfOrder;
        this.parentId = parentId;
        this.isComplex = isComplex;
    }

    public int getIndentId() {
        return indentId;
    }

    public void setIndentId(int indentId) {
        this.indentId = indentId;
    }

    public Shop getShopId_need() {
        return shopId_need;
    }

    public void setShopId_need(Shop shopId_need) {
        this.shopId_need = shopId_need;
    }

    public Shop getShopId_delivery() {
        return shopId_delivery;
    }

    public void setShopId_delivery(Shop shopId_delivery) {
        this.shopId_delivery = shopId_delivery;
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

    public Indent getParentId() {
        return parentId;
    }

    public void setParentId(Indent parentId) {
        this.parentId = parentId;
    }

    public Boolean isComplex()
    {
        return isComplex;
    }

    public void setIsComplex(Boolean isComplex)
    {
        this.isComplex = isComplex;
    }
}
