package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private int id;

    @Lob
    private String descriprion;

    @ManyToMany(mappedBy = "notifications")
    private List<User> userList = new ArrayList<>();

    @ManyToOne
    private Bug bug;

    private LocalDateTime creationDate;

    private String notificationType;


    public Notification(String descriprion, LocalDateTime creationDate, String notificationType) {
        this.descriprion = descriprion;
        this.creationDate = creationDate;
        this.notificationType = notificationType;
    }

    public Notification(String descriprion, Bug bug, LocalDateTime creationDate, String notificationType) {
        this.descriprion = descriprion;
        this.bug = bug;
        this.creationDate = creationDate;
        this.notificationType = notificationType;
    }

    public Notification(){this.creationDate = LocalDateTime.now();}
}
