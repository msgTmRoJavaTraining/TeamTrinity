package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String email;

    private boolean isActive;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToMany(mappedBy = "assignedTo")
    @EqualsAndHashCode.Exclude
    private List<Bug> assignedBuggs = new ArrayList<Bug>();

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    private List<Role> roles = new ArrayList<Role>();

    @OneToOne(cascade = CascadeType.REMOVE)
    private UserLogin userLogin;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private List<Notification> notifications = new ArrayList<>();


    public User(String name, String email, List<Role> roles,String phoneNumber,UserLogin userLogin) {
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.userLogin = userLogin;
    }

    public String getRolesNames(){

        StringBuilder roleBuilder = new StringBuilder();

        roles.forEach(role -> roleBuilder.append(role.getRoleName()).append(" "));

        return roleBuilder.toString();
    }

    public boolean getAccountActiveStatus() {
        return this.isActive;
    }

    @Override
    public String toString(){

        return "NUME:"+this.name+" EMAIL:"+this.email+" PHONE NUMBER:"
                +this.phoneNumber;
    }
}
