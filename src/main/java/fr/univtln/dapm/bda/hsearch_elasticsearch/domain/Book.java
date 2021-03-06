package fr.univtln.dapm.bda.hsearch_elasticsearch.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.search.engine.backend.types.ObjectStructure;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.TermVector;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

/**
 * Entité indexable représentant un livre.
 * 
 * @author vincent
 *
 */
@Entity
@Indexed
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	@FullTextField(analyzer = "m1_did_analyzer", projectable = Projectable.YES, termVector = TermVector.WITH_POSITIONS_OFFSETS)
	private String title;
	@FullTextField(analyzer = "m1_did_analyzer", projectable = Projectable.YES, termVector = TermVector.WITH_POSITIONS_OFFSETS)
	@Lob
	private String content;
	private transient double score;

	public Book(String title,String content, double score, Auteur auteur, Genre genre) {
		this.title = title;
		this.score = score;
		this.auteur = auteur;
		this.genre = genre;
		this.content = content;
	}

	public Book() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", content="
				+ (content.length() < 50 ? content.toString() : content.substring(0, 50)) + "]";
	}



	@ManyToOne
	@JoinColumn(name = "auteur_id")
	@IndexedEmbedded(structure = ObjectStructure.NESTED)
	private Auteur auteur;

	public Auteur getAuteur() {
		return auteur;
	}

	@ManyToOne
	@JoinColumn(name = "genre_id")
	@IndexedEmbedded(structure = ObjectStructure.NESTED)
	private Genre genre;

	public Genre getGenre() {
		return genre;
	}

}
