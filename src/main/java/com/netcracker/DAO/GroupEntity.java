package com.netcracker.DAO;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "group", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode( of = "id" )
@ToString( exclude = { "media" , "users" , "messages" } )
@RequiredArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class GroupEntity{

    @Id
    @SequenceGenerator( name = "group_sequence", sequenceName = "group_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "group_sequence" )
    @Column( name = "id" )
    private Long id;

    @Basic
    @Column( name = "name", unique = true, nullable = false )
    @NonNull
    private String name;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @PrimaryKeyJoinColumn
    private GroupMediaEntity media;

    @ManyToOne
    @JoinColumn( name = "owner_group" )
    @NonNull
    private PersonEntity owner;

    @ManyToMany( mappedBy = "groups" ) private List<PersonEntity> users;

    @OneToMany( mappedBy = "toGroup" )
    @OrderBy( "dateOfSubmition desc" )
    private List<MessageEntity> messages;
}
