package com.netcracker.DAO;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table( name = "group_media", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode( of = "id" )
@NoArgsConstructor(  )
public class GroupMediaEntity{

    @Id
    @GeneratedValue( generator = "group_foreign" )
    @GenericGenerator( strategy = "foreign",
                       name = "group_foreign",
                       parameters = @org.hibernate.annotations.Parameter( name = "property",
                                                                          value = "group" ) )
    @Column( name = "id" )
    private Long id;

    @Basic
    @Column( name = "image" )
    private byte[] image;

    @OneToOne( mappedBy = "media" ) GroupEntity group;
}
