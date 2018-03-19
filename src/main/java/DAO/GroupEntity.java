package DAO;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "group", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
@ToString( exclude = { "media" , "users" , "messages" } )
@RequiredArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class GroupEntity{
    @Id
//    @SequenceGenerator( name = "group_id", sequenceName = "group_id_seq" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;
    @Basic
    @Column( name = "name", unique = true, nullable = false )
    @NonNull
    private String  name;
    @Basic
    @Column( name = "media" )
    private byte[]  media;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "category", nullable = false )
    @NonNull
    private CategoryEntity category;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "owner_group" )
    @NonNull
    private PersonEntity owner;

    @ManyToMany( mappedBy = "groups" ) private List<PersonEntity> users;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "toGroup" ) private List<MessageEntity> messages;
}
