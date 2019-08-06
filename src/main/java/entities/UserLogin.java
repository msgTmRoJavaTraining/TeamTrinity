package entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_login")
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    private String password;

    @OneToOne
    private User user;

    public UserLogin(String password){

        this.password = password;
    }
}
