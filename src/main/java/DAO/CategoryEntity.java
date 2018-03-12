package DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "category", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
public class CategoryEntity{

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;

    @Basic
    @Column( name = "name", unique = true, insertable = false, updatable = false, nullable = false )
    private String name;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "category" ) List<GroupEntity> groups;

    @Override
    public String toString(){
        return "(CategoryEntity: id="+id+"; name="+name+")";
    }
}
