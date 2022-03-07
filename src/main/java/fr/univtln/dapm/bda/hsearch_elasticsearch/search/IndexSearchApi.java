package fr.univtln.dapm.bda.hsearch_elasticsearch.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Auteur;
import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Genre;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Book;
import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.BookResult;

/**
 * API pour l'indexation et la recherche de documents (des livres ici).
 * 
 * @author vincent
 *
 */
public class IndexSearchApi {
	public 	static 	Auteur a1 = new Auteur("test","teeeest");
	public static 	Genre g1 = new Genre("horor","desc");
	public static Book book1 = new Book("lol","test",12,a1,g1);



	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bda");
	public EntityManager entityManager2 = entityManagerFactory.createEntityManager();

	private EntityManager entityManager = entityManagerFactory.createEntityManager();
	private SearchSession fullTextSession = Search.session(entityManager);

	public void purgeIndex() {
		Search.mapping(entityManagerFactory).scope(Book.class).workspace().purge();
		fullTextSession.indexingPlan().execute();
	}

	public boolean indexFilesInFolder(String folderPath) throws IOException {
		entityManager.getTransaction().begin();
		Files.list(Paths.get(folderPath)).filter(Files::isRegularFile).forEach(t -> {
			try {
				indexFile(t);
			} catch (IOException e) {
				System.err.println("Cannot process " + t.toString());
			}
		});
		entityManager.getTransaction().commit();
		return true;
	}

	public boolean indexFile(Path path) throws IOException {
		String fileName = path.getFileName().toString();
		String fileContent = new String(Files.readAllBytes(path));



		Book book = new Book();




		book.setTitle(fileName);
		book.setContent(fileContent);
		entityManager.persist(a1);
		entityManager.persist(g1);
		entityManager.persist(book);
		entityManager.persist(book1); // le livre est automatiquement ajouté à la base de données et indexé!
		return true;
	}

	public List<BookResult> searchInTitle(String query) {
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f -> f.match().fields("title").matching(query)).fetchAllHits();

		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}


	public List<BookResult> searchkeyword(String query) {
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f -> f.match().fields("content").matching(query)).fetchAllHits();

		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}


	public List<BookResult> searchexact(String query) {
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f -> f.phrase().fields("content").matching(query)).fetchAllHits();

		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}

	public List<BookResult> searchexact2(String query1,String query2) {
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f -> f.bool().must( f.phrase().fields("title").matching(query1))
						.must( f.match().fields("content").matching(query2).fuzzy())).fetchAllHits();

		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}

	public List<BookResult> searchbook(Auteur auteur){
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f ->f.nested().objectField("auteur").nest(f.bool()
						.must(f.match().fields("auteur.nom").matching(auteur.getNom()))
						.must(f.match().fields("auteur.prenom").matching(auteur.getPrenom())))).fetchAllHits();



		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}

	public List<BookResult> serchgenre(Genre genre){
		List<BookResult> bookResults = new ArrayList<>();

		List<List<?>> results = fullTextSession.search(Book.class).select(f -> f.composite(f.score(), f.entity()))
				.where(f ->f.nested().objectField("genre").nest(f.bool()
						.must(f.match().fields("genre.type").matching(genre.getType()).fuzzy(2))
						.must(f.match().fields("genre.description").matching(genre.getDescription()).fuzzy(2)))).fetchAllHits();



		for (List<?> result : results) {
			float score = (float) result.get(0);
			Book book = (Book) result.get(1);
			bookResults.add(new BookResult(book, score));
		}

		return bookResults;
	}

	}



