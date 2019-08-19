package entities;

import entities.Bug;
import entities.User;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-19T05:21:27")
@StaticMetamodel(Notification.class)
public class Notification_ { 

    public static volatile ListAttribute<Notification, User> userList;
    public static volatile SingularAttribute<Notification, Bug> bug;
    public static volatile SingularAttribute<Notification, String> descriprion;
    public static volatile SingularAttribute<Notification, Integer> id;
    public static volatile SingularAttribute<Notification, String> notificationType;
    public static volatile SingularAttribute<Notification, LocalDateTime> creationDate;

}