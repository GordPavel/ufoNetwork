package DAO;

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
@ToString
@EqualsAndHashCode
public class MessageEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "writer" )
    private PersonEntity writer;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "to_group", nullable = false )
    private GroupEntity toGroup;

    @Basic
    @Column( name = "text", nullable = false )
    private String text;

    @Basic
    @Column( name = "date_of_submition", nullable = false )
    private Date   dateOfSubmition;

    @Basic
    @Column( name = "media" )
    private byte[] media;
}
