package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String email;

    private boolean isActive;

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

    @OneToOne
    private UserLogin userLogin;



}
