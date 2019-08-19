package entities;

import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-19T05:21:27")
@StaticMetamodel(UserLogin.class)
public class UserLogin_ { 

    public static volatile SingularAttribute<UserLogin, String> password;
    public static volatile SingularAttribute<UserLogin, Integer> id;
    public static volatile SingularAttribute<UserLogin, User> user;
    public static volatile SingularAttribute<UserLogin, String> username;

}