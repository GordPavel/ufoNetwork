package DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "planet", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
public class PlanetEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;
    @Basic
    @Column( name = "name", unique = true, updatable = false, nullable = false )
    private String  name;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "planet" ) private List<PersonEntity> persons;
    @Override
    public String toString(){
        return "(PlanetEntity: id="+id+"; name="+name+")";
    }
}
