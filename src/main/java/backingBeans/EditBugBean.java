package backingBeans;


import ejbs.BugEJB;
import entities.Bug;
import helpers.NavigationHelper;
import lombok.Data;
import org.primefaces.model.UploadedFile;
import security.WebHelper;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ManagedBean(name = "editBugBean")
@SessionScoped
public class EditBugBean implements Serializable {
    @Inject
    private BugEJB bugEJB;

    @Inject
    private NavigationHelper navigationHelper;

    private Bug editBug;

    private String title;
    private String description;
    private String revision;
    private String severity;
    private String assignedTo;
    private String createdBy;
    private String status;
    private List<String> statusList;
    private List<String> severityList;
    private List<String> availableAssignedToUserList;
    private byte[] attachment;

    private UploadedFile myFile;

    @PostConstruct
    public void init() {
        editBug = (Bug) WebHelper.getSession().getAttribute("bug");

        statusList = new ArrayList<>();
        severityList = new ArrayList<>();

        statusList.add("NEW"); statusList.add("IN PROGRESS"); statusList.add("INFO NEEDED"); statusList.add("FIXED"); statusList.add("REJECTED"); statusList.add("CLOSED");
        severityList.add("LOW"); severityList.add("MEDIUM"); severityList.add("HIGH"); severityList.add("CRITICAL");
        availableAssignedToUserList = bugEJB.getAllAvailableUsersForBugHandling(editBug.getAssignedTo());


        title = editBug.getTitle();
        description = editBug.getDescription();
        revision = editBug.getRevision();
        severity = editBug.getSeverity();
        assignedTo = editBug.getAssignedTo().getUserLogin().getUsername();
        status = editBug.getStatus();
        createdBy = editBug.getCreatedBy().getName();
    }

//    public void upload() {
//        if(myFile != null) {
//            FacesMessage message = new FacesMessage("#{msg.dialogMessages_editBug_attachments_upload_title}", myFile.getFileName() + " #{msg.dialogMessages_editBug_attachments_upload_messages}");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
//
//    }

    public void editBug() throws IOException {
        editBug.setTitle(title);
        editBug.setDescription(description);
        editBug.setRevision(String.valueOf(Integer.parseInt(revision) + 1));    //revision += 1
        editBug.setStatus(status);
        editBug.setSeverity(severity);
        editBug.setAssignedTo(bugEJB.getUserByUsername(assignedTo));

        bugEJB.editBugMonday(editBug);

        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, "Bug updated", "Successfuly updated bug");
        navigationHelper.customRedirectTo("bugManagement.xhtml");
    }
}
