package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "bugs")
@NoArgsConstructor
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String description;

    private String revision;

    private String fixedInVersion;

    private LocalDate targetData;

    @ManyToOne
    private User createdBy;

    private int status;

    @ManyToOne
    private User assignedTo;

    @OneToMany(mappedBy = "bug", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Attachment> attachments;


    public Bug(String title, String description, String revision, LocalDate targetData, User createdBy, int status, User assignedTo) {
        this.title = title;
        this.description = description;
        this.revision = revision;
        this.targetData = targetData;
        this.createdBy = createdBy;
        this.status = status;
        this.assignedTo = assignedTo;
    }
}
