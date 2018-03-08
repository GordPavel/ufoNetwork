package DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "race", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RaceEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;

    @Basic
    @Column( name = "name", unique = true, updatable = false, nullable = false )
    private String name;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "race" ) private List<PersonEntity> persons;
}
