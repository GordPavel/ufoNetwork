package com.netcracker.DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table( name = "message", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode( of = "id" )
@ToString( exclude = { "media" } )
public class MessageEntity{

    @Id
    @SequenceGenerator( name = "message_sequence", sequenceName = "message_id_seq" )
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

    @Basic
    @Column( name = "date_of_submition", insertable = false, updatable = false )
    private Date dateOfSubmition;

    @Basic
    @Column( name = "media" )
    private byte[] media;
}
