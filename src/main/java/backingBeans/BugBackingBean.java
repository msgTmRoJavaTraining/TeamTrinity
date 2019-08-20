package backingBeans;

import com.google.common.io.ByteStreams;
import ejbs.BugEJB;
import entities.Attachment;
import entities.Bug;
import entities.User;
import helpers.*;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ManagedBean(name = "bugBackingBean")
@SessionScoped
public class BugBackingBean implements Serializable {
    @Inject
    private DataGetter dataGetter;

    @Inject
    private BugEJB bugEJB;

    @Inject
    private XMLPDFGenerator xmlpdfGenerator;

    @Inject
    private SecurityHelper securityHelper;

    @Inject
    private NavigationHelper navigationHelper;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

    private String title;
    private String description;
    private String revision;
    private String targetDate;
    private String severity;
    private String statusName;
    private String assignedTo;
    private byte[] attachment;
    private Date selectedDate;
    private UploadedFile file;

    private List<Bug> bugList;
    private List<Bug> selectedBugs = new ArrayList<>();
    private Bug selectedBug;
    private User loggedInUser;
    private SimpleDateFormat format;
    private DefaultStreamedContent defaultStreamedContent;

    private List<String> availableAssignedToUserList;

    @PostConstruct
    public void init() {
        bugList = dataGetter.getBugs();

        loggedInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");
        format = new SimpleDateFormat("dd/MM/yyyy");

        availableAssignedToUserList = bugEJB.getAllAvailableUsersForBugHandling();
    }

    public void addBug2() {
        if (securityHelper.checkUserPermissions("BUG_MANAGEMENT", loggedInUser)) {
            if (!title.isEmpty() && title.length() >= 2) {
                if (!description.isEmpty() && description.length() >= 10) {
                    if (!revision.isEmpty() && revision.matches("[0-9].[0-9]")) {
                        try {
                            upload();
                            Attachment bugAttachemnt = createBugAttachemnt(file);
//                            System.out.println("File size is " + file.getSize() / 1048576);
                            bugEJB.createBug2(loggedInUser, title, description, revision, selectedDate, severity, assignedTo, bugAttachemnt);

                            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_addSuccessful_title"), languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_addSuccessful_message"));
                        } catch (Exception e) {
                            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_targetDateCheck_title"), languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_targetDateCheck_message"));
                        }
                    } else {
                        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_revisionCheck_title"), languagesBundleAccessor.getResourceBundleValue("dialog_bugBackingBean_addBug_revisionCheck_message"));
                    }
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

    private Attachment createBugAttachemnt(UploadedFile file) {
        Attachment createdAttachment = new Attachment();

        try {
            byte[] attachmentByteArray = ByteStreams.toByteArray(file.getInputstream());
            String retrieveFileName = file.getFileName();
            String[] partedFileName = retrieveFileName.split("\\.");
            String fileExtension = partedFileName[partedFileName.length-1];
            String fileMimeType = getFileMimeType(fileExtension);

            createdAttachment.setFileName(retrieveFileName);
            createdAttachment.setFileExtension(fileExtension);
            createdAttachment.setFileMimeType(fileMimeType);
            createdAttachment.setAttachmentBytes(attachmentByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return createdAttachment;
    }

    private String getFileMimeType(String fileExtension) {
        String mimeType = "";

        switch (fileExtension) {
            case "pdf":
                mimeType = "application/pdf";
                break;

            case "doc":
                mimeType = "application/msword";
                break;

            case "jpg":
                mimeType = "image/jpeg";
                break;

            case "png":
                mimeType = "image/png";
                break;

            case "odf":
                mimeType = "application/vnd.oasis.opendocument.formula";
                break;

            case "xlsx":
                mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;

            case "xls":
                mimeType = "application/vnd.ms-excel";
                break;
        }

        return mimeType;
    }

    public void onDateSelect(SelectEvent event) {
        targetDate = format.format(event.getObject());
    }

    public void rowSelect(SelectEvent event) {
        selectedBugs.add((Bug) event.getObject());
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void downloadPdf() {
        try {
            String fileName = "exported_employee.pdf";
            defaultStreamedContent = new DefaultStreamedContent(xmlpdfGenerator.objToPdf(selectedBugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
        } catch (Exception e) {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, "HEYY", "HASDASDSA");
        }
    }

    public void downloadExcel() {
        String fileName = "employees.xls";
        defaultStreamedContent = new DefaultStreamedContent(xmlpdfGenerator.objToExcel(selectedBugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }

    public void navigateTo(String page, Bug bug) {
        WebHelper.getSession().setAttribute("bug", bug);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StreamedContent downloadAttachment(Bug attachmentBug) {
        Attachment bugAttachment = attachmentBug.getAttachment();
        InputStream stream = new ByteArrayInputStream(bugAttachment.getAttachmentBytes());
        StreamedContent downloadFile = new DefaultStreamedContent(stream, bugAttachment.getFileMimeType(), bugAttachment.getFileName());

        return downloadFile;
    }
}
