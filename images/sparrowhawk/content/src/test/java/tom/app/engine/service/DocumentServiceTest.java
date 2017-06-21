package tom.app.engine.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import tom.app.engine.model.CustomTextEntity;
import tom.app.engine.model.LexicalExpressionList;
import tom.app.engine.model.PredefinedTextEntity;
import tom.app.engine.model.TextEntity;

public class DocumentServiceTest {

	private DocumentService sut;
	private LexicalExpressionList<TextEntity> catTokens;
	private LexicalExpressionList<TextEntity> catRegexs;
	private String url;
	
	private DocumentDao documentDao = mock(DocumentDao.class);
	private ListLoaderService loaderService = mock(ListLoaderService.class);
	
	@Before
	public void setUp() {
		sut = new DocumentService(documentDao, loaderService);
		catTokens = new LexicalExpressionList<>(
				Arrays.asList(
						new PredefinedTextEntity(UUID.randomUUID(), "cat1", "ginger"),
						new PredefinedTextEntity(UUID.randomUUID(), "cat2", "siamese")),
				"cats");
		catRegexs = new LexicalExpressionList<>(
				Arrays.asList(
						new CustomTextEntity(UUID.randomUUID(), "catre1", "[Pp]ersian"),
						new CustomTextEntity(UUID.randomUUID(), "catre2", "[Bb]ermese")),
				"catregexs");
		url = "www.test.local";
	}
	
	@Test
	public void shouldAddLexTermsToQuery() {

		QueryBuilder qb = sut.buildTermsQuery(catTokens, url);
		
		String queryString = qb.toString();
		assertThat(queryString).as("'ginger' token not in query string").contains("ginger");
		assertThat(queryString).as("'siamese' token not in query string").contains("siamese");
		assertThat(queryString).as("url not in query string").contains(url);
	}

	@Test
	public void shoudAddRegexTermsToQuerys() {
		List<QueryBuilder> qbs = sut.buildRegexQuerys(catRegexs, url);
		
		verifyRegexQueryBuilder(qbs.get(0), "[Pp]ersian");
		verifyRegexQueryBuilder(qbs.get(1), "[Bb]ermese");
	}

	private void verifyRegexQueryBuilder(QueryBuilder qb, String re) {
		String queryString = qb.toString();
		assertThat(queryString).as("'" + re + "' + regex not in query string").contains(re);
		assertThat(queryString).as("url not in query string").contains(url);
	}
}
