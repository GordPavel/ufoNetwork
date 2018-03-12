package DAO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "group", schema = "ufonetwork", catalog = "ufonetwork" )
@Getter
@Setter
@EqualsAndHashCode
public class GroupEntity{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE )
    @Column( name = "id" )
    private Integer id;
    @Basic
    @Column( name = "name", unique = true, nullable = false )
    private String  name;
    @Basic
    @Column( name = "media" )
    private byte[]  media;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "category", nullable = false )
    private CategoryEntity category;

    @ManyToOne( cascade = CascadeType.DETACH, fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "owner_group" )
    private PersonEntity owner;

    @ManyToMany( mappedBy = "groups" ) private List<PersonEntity> users;

    @OneToMany( cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER,
                mappedBy = "toGroup" ) private List<MessageEntity> messages;
    @Override
    public String toString(){
        return "(GroupEntity: id="+id+"; name="+name+ "; owner="+owner+")";
    }
}
