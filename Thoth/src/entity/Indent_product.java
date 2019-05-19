package entity;

import javax.persistence.*;

@Entity
@Table(name = "indent_product")
public class Indent_product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "IndentId",nullable = false)
    private Indent indentId;

    @ManyToOne
    @JoinColumn(name = "ProductId",nullable = false)
    private Product productId;

    @Column(name = "Amount",nullable = false)
    private int amount;

    public Indent_product(){

    }

    public Indent_product(Indent indentId, Product productId, int amount) {
        this.indentId = indentId;
        this.productId = productId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Indent getIndentId() {
        return indentId;
    }

    public void setIndentId(Indent indentId) {
        this.indentId = indentId;
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
}
