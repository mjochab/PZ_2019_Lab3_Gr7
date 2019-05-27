package entity;

import javax.persistence.*;

@Entity
@Table(name = "user_shop")
public class UserShop {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ShopId")
    private Shop shopId;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    private User userId;

    public UserShop(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
