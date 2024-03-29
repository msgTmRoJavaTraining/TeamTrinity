package ejbs;

import com.google.common.io.ByteStreams;
import entities.Bug;
import entities.User;
import security.WebHelper;


import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Stateless
public class BugEJB implements Serializable {

    @PersistenceContext(unitName = "java.training")
    private EntityManager entityManager;


    public Bug createBug(InputStream inputStream, String title, String description, String targetDate, String revision,
                         String assignedTo, String severity,byte[] attachment) throws IOException {

        User logInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");
        attachment= ByteStreams.toByteArray(inputStream);

        Bug bug = new Bug();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setRevision(revision);
        bug.setStatus("NEW");
        bug.setAttachment(attachment);

        try {
            Query queryCreatedBy = entityManager.createQuery("select user from User user where user.userLogin.username=:createdBy");
            queryCreatedBy.setParameter("createdBy",logInUser.getUserLogin().getUsername());
            User user = (User) queryCreatedBy.getSingleResult();
            bug.setCreatedBy(user);

            try {
                Query queryAssignedTo = entityManager.createQuery("select user from User user where user.name=:assignedTo");
                queryAssignedTo.setParameter("assignedTo", assignedTo);
                User user1 = (User) queryAssignedTo.getSingleResult();
                bug.setAssignedTo(user1);
                bug.setSeverity(severity);

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

                    //convert String to LocalDate
                    LocalDate localDate = LocalDate.parse(targetDate, formatter);
                    bug.setTargetData(localDate);

                    entityManager.persist(bug);
                }catch (Exception e){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid Argument", "Wrong target date format"));
                }
            }catch (Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Incorect arguments", "AssignetTo user do not exist"));
            }
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Incorect arguments", "CreatedBy user do not exist"));
        }

        return bug;
    }

    public void editBug(Bug bug,String title,String description,String revision,
                        String severity,byte[] attachment) throws IOException {




        bug.setTitle(title);
        bug.setDescription(description);
        bug.setRevision(revision);
        bug.setSeverity(severity);
        

        entityManager.merge(bug);
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
