package entities;

import entities.Right;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-20T07:50:59")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile ListAttribute<Role, Right> rights;
    public static volatile SingularAttribute<Role, String> roleName;
    public static volatile SingularAttribute<Role, Integer> id;
    public static volatile ListAttribute<Role, User> user;

}