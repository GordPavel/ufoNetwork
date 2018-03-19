package DAO;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table( name = "person", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
@ToString( exclude = { "media" , "race" , "groups" , "rulingGroups" , "messages" } )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@RequiredArgsConstructor
public class PersonEntity{
    @Id
//    @SequenceGenerator( name = "person_id", sequenceName = "person_id_seq" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer           id;
    @Basic
    @Column( name = "login", unique = true, nullable = false )
    @NonNull
    private String            login;
    @Basic
    @Column( name = "pass", nullable = false )
    @NonNull
    private String            pass;
    @Basic
//    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "date_of_registration", insertable = false, updatable = false )
    private ZonedDateTime     dateOfRegistration;
    @Basic
    @Column( name = "sex" )
    private String            sex;
    @Basic
    @Column( name = "age" )
    private Integer           age;
    @Basic
    @Column( name = "media" )
    private byte[]            media;
    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "race", nullable = false )
    @NonNull
    private RaceEntity        race;
    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "planet" )
    private PlanetEntity      planet;
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

}
