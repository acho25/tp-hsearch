package fr.univtln.dapm.bda.hsearch_elasticsearch.search;


import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;


public class M1DidAnalyzer implements ElasticsearchAnalysisConfigurer {
	/**
	 * Analyseur utilisant Opensearch
	 * Si Opensearch à été lancé avec docker tous les livres indexés se trouverons dans le repertoire opensearch-data (dans docker-opensearch)
	 * Les données seront manipulable avec opensearch et opensearch-dashboard (voir README partie 2 pour preuve)
	 *
	 * @param context la configuration de l'analyseur
	 */
	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer("m1_did_analyzer").custom()
				.tokenizer("standard")
				.tokenFilters("lowercase", "snowball_english", "asciifolding");

		context.tokenFilter("snowball_english").type("snowball").param("language", "English");
	}
}
