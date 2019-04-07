package entity;

import javax.persistence.*;

@Entity
@Table(name = "user_object")
public class User_object {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "ObjectId")
    private int objectId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "UserId")
    private int userId;

    public User_object(){

    }

    public User_object(int objectId, int userId) {
        this.objectId = objectId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User_object{" +
                "id=" + id +
                ", objectId=" + objectId +
                ", userId=" + userId +
                '}';
    }
}
