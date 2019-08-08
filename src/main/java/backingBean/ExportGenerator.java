package backingBean;

import lombok.Data;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.primefaces.model.DefaultStreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Named
@SessionScoped
public class ExportGenerator implements Serializable {


    @Inject
    private XMLPDFGenerator theEjb;
    private DefaultStreamedContent dStream;
    private List employees=new ArrayList();
    private String name;
    private String cnp;
    private String role;

    public void downloadPdf(){
        String fileName = "exported_employee.pdf";
        dStream =new DefaultStreamedContent(theEjb.objToPdf(employees), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }
    public void downloadExcel(){
        String fileName="employees.xls";
        dStream =new DefaultStreamedContent(theEjb.objToExcel(employees), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);


    }


}

