package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_login")
@NoArgsConstructor
public class UserLogin implements Serializable {

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
