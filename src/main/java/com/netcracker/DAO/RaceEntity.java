package com.netcracker.DAO;

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
@EqualsAndHashCode( of = "id" )
@ToString( exclude = "persons" )
public class RaceEntity{

    @Id
    @SequenceGenerator( name = "race_sequence", sequenceName = "race_id_seq", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "race_sequence" )
    @Column( name = "id" )
    private Long id;

    @Basic
    @Column( name = "name", unique = true, updatable = false, nullable = false )
    private String name;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "race" ) private List<PersonEntity> persons;
}
