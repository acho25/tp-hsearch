package fr.univtln.dapm.bda.hsearch_elasticsearch.search;


import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;


public class M1DidAnalyzer implements ElasticsearchAnalysisConfigurer {

	@Override
	public void configure(ElasticsearchAnalysisConfigurationContext context) {
		context.analyzer( "m1_did_analyzer" ).custom()
				.tokenizer( "standard" )
				.tokenFilters( "lowercase", "snowball_english", "asciifolding" );

		context.tokenFilter( "snowball_english" )
				.type( "snowball" )
				.param( "language", "English" )
				.param("minGramSize", "3")
				.param("maxGramSize", "7");

		context.tokenFilter( "snowball_french" )
				.type( "snowball" )
				.param( "language", "French" )
				.param("minGramSize", "3")
				.param("maxGramSize", "7");;


	}

}
