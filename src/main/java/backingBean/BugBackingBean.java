package backingBean;

import entities.User;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.time.LocalDate;

@Data
@ManagedBean(name = "bugBackingBean")
@ApplicationScoped
public class BugBackingBean {

    private String title;
    private String description;
    private String revision;
    private String fixedInVersion;
    private LocalDate targetDate;
    private String createdBy;
    private String assignedTo;


    @Inject
    private DatabaseBugEJB bugEJB;

    public void addBug(){

        bugEJB.createBug(title,description,revision,fixedInVersion,createdBy,assignedTo);
    }
}
