package backingBean;

import Enums.SeverityName;
import Enums.SeverityName;
import Enums.StatusName;
import entities.Bug;
import entities.User;
import lombok.Data;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
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
    private String targetDate;
    private String createdBy;
    private String severity;
    private String statusName;
    private String assignedTo;

    private List<Bug> bugList;
    private Bug selectedBug;


    @Inject
    private DatabaseBugEJB bugEJB;

    @Inject
    private DataGetter dataGetter;

    @PostConstruct
    public void init(){
        bugList=dataGetter.getBug();
    }

    public void addBug(){
        bugEJB.createBug(title,description,targetDate,revision,fixedInVersion,createdBy,assignedTo,severity);
    }

    public void rowSelect(SelectEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("editBug.xhtml");
    }
}
