package ejbs;

import com.google.common.io.ByteStreams;
import entities.Attachment;
import entities.Bug;
import entities.User;
import security.WebHelper;


import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Stateless
public class BugEJB implements Serializable {
    @PersistenceContext(unitName = "java.training")
    private EntityManager entityManager;

    public void createBug2(User loggedInUser, String bugTitle, String bugDescription, String bugVersionRevision, Date bugTargetDate, String bugSeverity, String bugAssignedTo, Attachment attachment) {
        Bug toBeCreatedBug = new Bug();

        //Find assignedToUser
        User assignedToBugUser = getUserByUsername(bugAssignedTo);

        //Create new bug
        toBeCreatedBug.setTitle(bugTitle);
        toBeCreatedBug.setDescription(bugDescription);
        toBeCreatedBug.setRevision(bugVersionRevision);
        toBeCreatedBug.setFixedInVersion("Unknown");
        toBeCreatedBug.setStatus("NEW");
        toBeCreatedBug.setSeverity(bugSeverity);
        toBeCreatedBug.setTargetData(bugTargetDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        toBeCreatedBug.setCreatedBy(loggedInUser);
        toBeCreatedBug.setAssignedTo(assignedToBugUser);
        toBeCreatedBug.setAttachment(attachment);

        System.out.println(toBeCreatedBug.toString());

        entityManager.persist(toBeCreatedBug);
    }

    public void editBugMonday(Bug toBeEdittedBug) {
        entityManager.merge(toBeEdittedBug);
    }

    public List<String> getAllAvailableUsersForBugHandling() {
        TypedQuery<String> query = entityManager.createQuery("select user.userLogin.username from User as user", String.class);

        return query.getResultList();
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select user from User as user where user.userLogin.username = :givenUsername", User.class);
        query.setParameter("givenUsername", username);

        return query.getSingleResult();
    }
}
