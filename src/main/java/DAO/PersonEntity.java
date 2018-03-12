package DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table( name = "person", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
public class PersonEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;
    @Basic
    @Column( name = "login", unique = true, nullable = false )
    private String  login;
    @Basic
    @Column( name = "pass", nullable = false )
    private String  pass;
    @Basic
    @Column( name = "date_of_registration", nullable = false )
    private Date    dateOfRegistration;
    @Basic
    @Column( name = "sex" )
    private String  sex;
    @Basic
    @Column( name = "age" )
    private Integer age;
    @Basic
    @Column( name = "media" )
    private byte[]  media;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "race", nullable = false )
    private RaceEntity race;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "planet" )
    private PlanetEntity planet;

    @ManyToMany( cascade = { CascadeType.DETACH } )
    @JoinTable( name = "person_group",
                joinColumns = { @JoinColumn( name = "person" ) },
                inverseJoinColumns = { @JoinColumn( name = "group" ) } )
    private List<GroupEntity> groups;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "owner" ) private List<GroupEntity> rulingGroups;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "writer" ) private List<MessageEntity> messages;

    @Override
    public String toString(){
        return "(PersonEntity: id="+id+"; login="+login+ "; sex="+sex+"; age="+age
                +"; planet="+planet+"; race="+race+"; registration="+dateOfRegistration+")";
    }
}
