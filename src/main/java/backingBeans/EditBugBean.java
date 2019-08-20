package backingBeans;


import ejbs.BugEJB;
import entities.Bug;
import entities.User;
import helpers.LanguagesBundleAccessor;
import helpers.NavigationHelper;
import helpers.SecurityHelper;
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
import java.text.DecimalFormat;
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

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

    @Inject
    private SecurityHelper securityHelper;

    private Bug editBug;
    private User loggedInUser;
    private boolean canUserCloseBug = false;
    private boolean canUserEditBug = false;

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
        loggedInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");

        if(securityHelper.checkUserPermissions("BUG_MANAGEMENT", loggedInUser)) {
            canUserEditBug = true;
        }

        if(securityHelper.checkUserPermissions("BUG_CLOSE", loggedInUser)) {
            canUserCloseBug = true;
        }

        statusList = new ArrayList<>();
        severityList = new ArrayList<>();

        statusList.add("NEW"); statusList.add("IN PROGRESS"); statusList.add("INFO NEEDED"); statusList.add("FIXED"); statusList.add("REJECTED");
        severityList.add("LOW"); severityList.add("MEDIUM"); severityList.add("HIGH"); severityList.add("CRITICAL");
        availableAssignedToUserList = bugEJB.getAllAvailableUsersForBugHandling();


        title = editBug.getTitle();
        description = editBug.getDescription();
        revision = editBug.getRevision();
        severity = editBug.getSeverity();
        assignedTo = editBug.getAssignedTo().getUserLogin().getUsername();
        status = editBug.getStatus();
        createdBy = editBug.getCreatedBy().getUserLogin().getUsername();
    }

//    public void upload() {
//        if(myFile != null) {
//            FacesMessage message = new FacesMessage("#{msg.dialogMessages_editBug_attachments_upload_title}", myFile.getFileName() + " #{msg.dialogMessages_editBug_attachments_upload_messages}");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        }
//
//    }

    public void editBug() throws IOException {
        if(securityHelper.checkUserPermissions("BUG_MANAGEMENT", loggedInUser)) {
            if(!title.isEmpty() && title.length() >= 2) {
                if(!description.isEmpty() && description.length() >= 10) {
                    editBug.setTitle(title);
                    editBug.setDescription(description);
                    editBug.setStatus(status);
                    editBug.setSeverity(severity);
                    editBug.setAssignedTo(bugEJB.getUserByUsername(assignedTo));

                    if(editBug.getRevision().endsWith(".9")) {
                        editBug.setRevision(String.valueOf(new DecimalFormat("#.#").format(Double.parseDouble(revision.split("\\.")[0]) + 1.0)));    //revision += 1.0 (3.9 -> 4.0)
                    } else {
                        editBug.setRevision(String.valueOf(new DecimalFormat("#.#").format(Double.parseDouble(revision) + 0.1)));    //revision += 0.1
                    }

                    bugEJB.editBugMonday(editBug);

                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editActionSuccess_title"), languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editActionSuccess_message"));
                    navigationHelper.customRedirectTo("bugManagement.xhtml");
                } else {
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_description_title"), languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_description_message"));
                }
            } else {
                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_bugTitle_title"), languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_bugTitle_message"));
            }
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_message"));
            navigationHelper.customRedirectTo("homepage.xhtml");
        }
    }

    public void closeBug() {
        if(securityHelper.checkUserPermissions("BUG_CLOSE", loggedInUser)) {
            if(status.equals("FIXED")) {
                editBug.setStatus("CLOSED");
                bugEJB.editBugMonday(editBug);

                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_closeActionSuccess_title"), languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_closeActionSuccess_message"));
                navigationHelper.customRedirectTo("bugManagement.xhtml");
            } else {
                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_closeCondition_title"), languagesBundleAccessor.getResourceBundleValue("dialog_editBugBean_editAction_validations_closeCondition_message"));
            }
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_message"));
            navigationHelper.customRedirectTo("homepage.xhtml");
        }
    }
}
