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
@EqualsAndHashCode( of = "id" )
@ToString( exclude = "persons" )
public class PlanetEntity{
    @Id
    @SequenceGenerator( name = "planet_id", sequenceName = "planet_id_seq" )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "planet_id" )
    @Column( name = "id" )
    private                                   Long               id;
    @Basic
    @Column( name = "name", unique = true, updatable = false, nullable = false )
    private                                   String             name;
    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "planet" ) private List<PersonEntity> persons;
}
