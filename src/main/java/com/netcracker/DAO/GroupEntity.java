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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private                                    Long                id;
    @Basic
    @Column( name = "name", unique = true, nullable = false )
    @NonNull
    private                                    String              name;
    @Basic
    @Column( name = "media" )
    private                                    byte[]              media;
//    todo Осторожнее с полями, у которых FetchType.LAZY, потому что они загружаются лениво, то
// есть только после первого обращения к ним
    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.LAZY )
    @JoinColumn( name = "owner_group" )
    @NonNull
    private                                    PersonEntity        owner;
    @ManyToMany( mappedBy = "groups" ) private List<PersonEntity>  users;
    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "toGroup" ) private List<MessageEntity> messages;
}
