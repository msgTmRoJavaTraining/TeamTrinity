package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rights")
@NoArgsConstructor
public class Right implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "right_name")
    private String rightName;

    @ManyToMany(mappedBy = "rights")
    @EqualsAndHashCode.Exclude
    private List<Role> rolesRight = new ArrayList<>();

    public Right(String rightName){
        this.rightName = rightName;
    }

    @Override
    public String toString(){return rightName;}
}
