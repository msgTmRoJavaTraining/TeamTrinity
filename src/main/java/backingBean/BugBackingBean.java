package backingBean;

import entities.Bug;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ManagedBean(name = "bugBackingBean")
@SessionScoped
public class BugBackingBean implements Serializable {

    private String title;
    private String description;
    private String revision;
    private String fixedInVersion;
    private String targetDate;
    private String createdBy;
    private String severity;
    private String statusName;
    private String assignedTo;
    private byte[] attachment;
    private Date selectedDate;

    private List<Bug> bugList;
    private List<Bug> selectedBugs=new ArrayList<>();
    private Bug selectedBug;

    @Inject
    private DatabaseBugEJB bugEJB;

    @Inject
    private XMLPDFGenerator xmlpdfGenerator;


    private DefaultStreamedContent defaultStreamedContent;

    @Inject
    private DataGetter dataGetter;

    @Inject
    private FileUploadBean fileUploadBean;

    @PostConstruct
    public void init(){
        bugList=dataGetter.getBugs();
    }

    public void addBug() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

//      fileUploadBean.upload();
        upload();
        bugEJB.createBug(file.getInputstream(), title,description,format.format(selectedDate),revision,fixedInVersion,createdBy,assignedTo,severity,attachment);
    }

    public void onDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        targetDate = format.format(event.getObject());
    }

    public void rowSelect(SelectEvent event) {
        selectedBugs.add((Bug) event.getObject());
//        FacesMessage msg = new FacesMessage("Bug Selected", event.getObject().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private UploadedFile file;

    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void downloadPdf(){
        String fileName = "exported_employee.pdf";
        defaultStreamedContent =new DefaultStreamedContent(xmlpdfGenerator.objToPdf(selectedBugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }
    public void downloadExcel(){
        String fileName="employees.xls";
        defaultStreamedContent =new DefaultStreamedContent(xmlpdfGenerator.objToExcel(selectedBugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);


    }
}
