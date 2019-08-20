package entities;

import entities.Bug;
import entities.Notification;
import entities.Role;
import entities.UserLogin;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-20T09:11:05")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, UserLogin> userLogin;
    public static volatile SingularAttribute<User, String> phoneNumber;
    public static volatile ListAttribute<User, Role> roles;
    public static volatile SingularAttribute<User, String> name;
    public static volatile ListAttribute<User, Bug> assignedBuggs;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, Boolean> isActive;
    public static volatile SingularAttribute<User, String> email;
    public static volatile ListAttribute<User, Notification> notifications;

}