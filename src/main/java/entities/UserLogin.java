package entities;

import javax.persistence.*;

public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    private String password;

    @OneToOne
    private User user;
}
