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
@EqualsAndHashCode( of = "id" )
@ToString( exclude = "groups" )
public class CategoryEntity{
    @Id
    @SequenceGenerator( name = "category_id", sequenceName = "category_id_seq" )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "category_id" )
    @Column( name = "id" )
    private                                                      Long              id;
    @Basic
    @Column( name = "name", unique = true, insertable = false, updatable = false, nullable = false )
    private                                                      String            name;
    @OneToMany( fetch = FetchType.EAGER, mappedBy = "category" ) List<GroupEntity> groups;
}
