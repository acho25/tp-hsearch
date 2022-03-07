package fr.univtln.dapm.bda.hsearch_elasticsearch;

import java.io.IOException;

import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Auteur;
import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Book;
import fr.univtln.dapm.bda.hsearch_elasticsearch.domain.Genre;
import fr.univtln.dapm.bda.hsearch_elasticsearch.search.IndexSearchApi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
	public static void main(String[] args) throws IOException {







		// Instanciation de notre classe IndexSearchApi pour indexer et rechercher
		IndexSearchApi api = new IndexSearchApi();


		// Réindexation à chaque nouvel appel de la classe Main (à commenter si besoin).
		api.purgeIndex();
		api.indexFilesInFolder(Main.class.getResource("/data/raw").getFile());


		System.out.println("Recherche de 'pilgrime tale' dans le titre : "
				+ api.searchInTitle("pilgrimge tale")+"\n");


		System.out.println("Recherche de 'pilgrime tale' dans le content : "
				+ api.searchkeyword("pilgrame")+"\n");

		System.out.println("Recherche de 'It was an appreciation ' dans le content : "
				+ api.searchexact("It was an appreciation ")+"\n");

		System.out.println("Recherche de 'Mahatma ' dans le titre et sinking dans content : "
				+ api.searchexact2("Mahatma","sinking")+"\n");

		System.out.println("Recherche du livre 'lol' par nom d'auteur : "
				+ api.searchbook(IndexSearchApi.a1)+"\n");


		System.out.println("Recherche de livre 'lol' par genre  : "
				+ api.serchgenre(IndexSearchApi.g1));

		System.exit(0);














	}
}
