package backingBean;

import Enums.SeverityName;
import Enums.SeverityName;
import Enums.StatusName;
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
    private String targetDate;
    private String createdBy;
    private String severity;
    private String statusName;
    private String assignedTo;


    @Inject
    private DatabaseBugEJB bugEJB;

    public void addBug(){

        bugEJB.createBug(title,description,targetDate,revision,fixedInVersion,createdBy,assignedTo,severity);
    }
}
