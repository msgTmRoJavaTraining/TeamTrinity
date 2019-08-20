package entities;

import entities.User;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-08-20T09:11:05")
@StaticMetamodel(Bug.class)
public class Bug_ { 

    public static volatile SingularAttribute<Bug, String> severity;
    public static volatile SingularAttribute<Bug, String> fixedInVersion;
    public static volatile SingularAttribute<Bug, LocalDate> targetData;
    public static volatile SingularAttribute<Bug, byte[]> attachment;
    public static volatile SingularAttribute<Bug, User> createdBy;
    public static volatile SingularAttribute<Bug, String> description;
    public static volatile SingularAttribute<Bug, Integer> id;
    public static volatile SingularAttribute<Bug, String> title;
    public static volatile SingularAttribute<Bug, User> assignedTo;
    public static volatile SingularAttribute<Bug, String> revision;
    public static volatile SingularAttribute<Bug, String> status;

}