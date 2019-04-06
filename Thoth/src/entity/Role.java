package entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "RoleId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    @Column(name = "Position")
    private String position;

    public Role(){

    }

    public Role(int roleId, String position) {
        this.roleId = roleId;
        this.position = position;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", position='" + position + '\'' +
                '}';
    }
}
