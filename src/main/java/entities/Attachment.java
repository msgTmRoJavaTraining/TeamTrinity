package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@Table(name = "attachments")
@NoArgsConstructor
public class Attachment {

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
