package backingBean;

import entities.Bug;
import entities.User;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

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

    private List<Bug> bugList;


    @Inject
    private DatabaseBugEJB bugEJB;

    @Inject
    private DataGetter dataGetter;

    @PostConstruct
    public void init(){
        bugList=dataGetter.getBug();
    }

    public void addBug(){

        bugEJB.createBug(title,description,revision,fixedInVersion,createdBy,assignedTo);
    }
}
