package backingBeans;

import lombok.Data;
import org.primefaces.model.UploadedFile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@ViewScoped
@Data
@ManagedBean(name = "fileUploadBean")
public class FileUploadBean implements Serializable {

    private UploadedFile file;

    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("#{msg.dialogMessages_fileUploadBean_attachments_upload_title}", file.getFileName() + " #{msg.dialogMessages_fileUploadBean_attachments_upload_messages}");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

}
