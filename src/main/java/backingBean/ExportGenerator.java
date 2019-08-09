package backingBean;

import entities.Bug;
import lombok.Data;
import org.primefaces.model.DefaultStreamedContent;

import javax.annotation.PostConstruct;
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

    @Inject
    private DataGetter dataGetter;

    private DefaultStreamedContent dStream;


    private List<Bug> bugs = new ArrayList<>();

    @PostConstruct
    public void init(){
        bugs = dataGetter.getBugs();
    }



    public void downloadPdf(){
        String fileName = "exported_employee.pdf";
        dStream =new DefaultStreamedContent(theEjb.objToPdf(bugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);
    }
    public void downloadExcel(){
        String fileName="employees.xls";
        dStream =new DefaultStreamedContent(theEjb.objToExcel(bugs), FacesContext.getCurrentInstance().getExternalContext().getMimeType(fileName), fileName);


    }


}

