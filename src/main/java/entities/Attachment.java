package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "attachments")
@NoArgsConstructor
public class Attachment implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Lob
    private byte[] content;

    private String name;

    @ManyToOne
    private Bug bug;

    public Attachment(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }
}
