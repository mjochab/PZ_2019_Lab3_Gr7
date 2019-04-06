package entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_object")
public class User_object {

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private int objectId;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private int userId;

    public User_object(){

    }

    public User_object(int objectId, int userId) {
        this.objectId = objectId;
        this.userId = userId;
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
                "objectId=" + objectId +
                ", userId=" + userId +
                '}';
    }
}
