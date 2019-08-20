package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "bugs")
@NoArgsConstructor
public class Bug implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String description;

    private String revision;

    private String fixedInVersion;

    private LocalDate targetData;

    private byte[] attachment;

    @ManyToOne
    private User createdBy;

    private String status;

    private String severity;

    @ManyToOne
    private User assignedTo;

//    @OneToMany(mappedBy = "bug", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
//    private List<Attachment> attachments;


    public Bug(String title, String description, String revision, LocalDate targetData, User createdBy, String status, User assignedTo,byte[] attachment) {
        this.title = title;
        this.description = description;
        this.revision = revision;
        this.targetData = targetData;
        this.createdBy = createdBy;
        this.status = status;
        this.assignedTo = assignedTo;
        this.attachment=attachment;

    }

    @Override
    public String toString(){

        return "BUG:"+"Title:"+this.title+" Description:"+this.description+" Target date:"+
                this.targetData+" Created By:"+this.createdBy+"Status:"+this.status+
                "Assigned TO:"+ this.assignedTo;
    }


}
