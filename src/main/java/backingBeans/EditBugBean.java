package backingBean;


import entities.Bug;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;
import security.WebHelper;


import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Data
@ManagedBean(name = "editBugBean")
@SessionScoped
public class EditBugBean implements Serializable {

    @Inject
    private DatabaseBugEJB databaseBugEJB;


    private String title;
    private String description;
    private String revision;
    private String severity;
    private String assignedTo;
    private byte[] attachment;

    private UploadedFile myFile;



    public void upload() {
        if(myFile != null) {
            FacesMessage message = new FacesMessage("#{msg.dialogMessages_editBug_attachments_upload_title}", myFile.getFileName() + " #{msg.dialogMessages_editBug_attachments_upload_messages}");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void editBug() throws IOException {


        Bug bug = (Bug) WebHelper.getSession().getAttribute("bug");
        //upload();
        databaseBugEJB.editBug(bug,title,description,revision,severity,attachment);
    }
}
