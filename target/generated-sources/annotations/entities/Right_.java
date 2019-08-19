package entities;

import entities.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-18T12:03:53")
@StaticMetamodel(Right.class)
public class Right_ { 

    public static volatile SingularAttribute<Right, String> rightName;
    public static volatile ListAttribute<Right, Role> rolesRight;
    public static volatile SingularAttribute<Right, Integer> id;

}