package backingBean;


import Enums.SeverityName;
import Enums.StatusName;
import com.google.common.io.ByteStreams;
import entities.Bug;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.glassfish.jersey.Severity;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Stateless
public class DatabaseBugEJB {

    @PersistenceContext(unitName = "java.training")
    private EntityManager entityManager;

    public Bug createBug(InputStream inputStream, String title, String description, String targetDate, String revision, String fixedInVersion, String createdBy,
                         String assignedTo, String severity, byte[] attachment) throws IOException {

        Bug bug = new Bug();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setRevision(revision);
        bug.setFixedInVersion(fixedInVersion);
        bug.setStatus("NEW");

        InputStream fileInputStream = inputStream;
        attachment= ByteStreams.toByteArray(inputStream);
        bug.setAttachment(attachment);

        try {
            Query queryCreatedBy = entityManager.createQuery("select user from User user where user.name=:createdBy");
            queryCreatedBy.setParameter("createdBy", createdBy);
            User user = (User) queryCreatedBy.getSingleResult();
            bug.setCreatedBy(user);


            Query queryAssignedTo = entityManager.createQuery("select user from User user where user.name=:assignedTo");
            queryAssignedTo.setParameter("assignedTo", assignedTo);
            User user1 = (User) queryAssignedTo.getSingleResult();
            bug.setAssignedTo(user1);
            bug.setSeverity(severity);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

            //convert String to LocalDate
            LocalDate localDate = LocalDate.parse(targetDate, formatter);
            bug.setTargetData(localDate);
            entityManager.persist(bug);
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Argument", e.getLocalizedMessage()));
        }




        return bug;
    }

    public void editBug(String name) {


    }

    public User readUser(String name){

        User user = entityManager.find(User.class,name);

        return user;
    }

    public User updateUser(){

        //will be completed wednesday 07.08.2019
        return null;
    }
}
