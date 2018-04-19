package com.netcracker.DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table( name = "person_media", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode( of = "id" )
@NoArgsConstructor
public class PersonMediaEntity{

    @Id
    @GeneratedValue( generator = "person_foreign" )
    @GenericGenerator( strategy = "foreign",
                       name = "person_foreign",
                       parameters = @org.hibernate.annotations.Parameter( name = "property",
                                                                          value = "person" ) )
    @Column( name = "id" )
    private Long id;

    @Basic
    @Column( name = "image" )
    private byte[] image;

    @OneToOne( mappedBy = "media" )
    PersonEntity person;
}
