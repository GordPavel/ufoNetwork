package com.netcracker.DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table( name = "message", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode( of = "id" )
public class MessageEntity{

    @Id
    @SequenceGenerator( name = "message_sequence",
                        sequenceName = "message_id_seq",
                        allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "message_sequence" )
    @Column( name = "id" )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "writer" )
    private PersonEntity writer;

    @ManyToOne( optional = false )
    @JoinColumn( name = "to_group", nullable = false )
    private GroupEntity toGroup;

    @Basic
    @Column( name = "text", nullable = false )
    private String text;

    @Column( name = "date_of_submition", nullable = false, insertable = false, updatable = false )
    @Convert( converter = ZonedDateTimeConverter.class )
    private ZonedDateTime dateOfSubmition;
}
