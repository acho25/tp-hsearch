package fr.univtln.dapm.bda.hsearch_elasticsearch.domain;


import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.TermVector;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @FullTextField(analyzer = "m1_did_analyzer", projectable = Projectable.YES, termVector = TermVector.WITH_POSITIONS_OFFSETS)
    private String type ;
    @FullTextField(analyzer = "m1_did_analyzer", projectable = Projectable.YES, termVector = TermVector.WITH_POSITIONS_OFFSETS)
    private String description;

    public Genre(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Genre() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch =  FetchType.LAZY,mappedBy = "genre")
    private List<Book> books = new ArrayList<>();


}
