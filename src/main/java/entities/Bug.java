package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

}
