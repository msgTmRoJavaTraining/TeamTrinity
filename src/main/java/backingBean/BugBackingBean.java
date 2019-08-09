package backingBean;

import entities.Bug;
import lombok.Data;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Data
@Named
@ApplicationScoped
public class BugBackingBean  {

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
        bugList=dataGetter.getBugs();
    }

    public void addBug(){
        bugEJB.createBug(title,description,targetDate,revision,fixedInVersion,createdBy,assignedTo,severity);
    }

    public void rowSelect(SelectEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("editBug.xhtml");
    }
}
