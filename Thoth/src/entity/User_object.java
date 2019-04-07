package entity;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

@Entity
@Table(name = "user_object")
public class User_object {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private Shop objectId;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User userId;

    public User_object(){

    }

    public User_object(Shop objectId, User userId) {
        this.objectId = objectId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shop getObjectId() {
        return objectId;
    }

    public void setObjectId(Shop objectId) {
        this.objectId = objectId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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
