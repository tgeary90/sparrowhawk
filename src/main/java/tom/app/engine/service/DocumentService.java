package tom.app.engine.service;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tom.app.engine.model.LexicalExpressionList;
import tom.app.engine.model.TextEntity;
import tom.app.engine.model.WebPage;

@Component
public class DocumentService {

	private DocumentDao documentDao;
	private ListLoaderService loaderService;
	
	public enum Action { BLOCK, ALLOW };
	
	@Autowired
	public DocumentService(DocumentDao documentDao, ListLoaderService loaderService) {
		this.documentDao = documentDao;
		this.loaderService = loaderService;
	}

	
	public String index(WebPage webPage, String subId) {
		return documentDao.index(webPage, subId);
	}

	public WebPage get(String subId, String docId) {
		return documentDao.get(docId, subId);
	}

	public String search(String subId, WebPage webPage) {
		return documentDao.getDocId(subId, webPage.getUrl(), "webpage", "url");
	}

	public Action filter(String subId, WebPage webPage) {
		List<LexicalExpressionList<? extends TextEntity>> reLists = loaderService.getRegexLists();
		List<LexicalExpressionList<? extends TextEntity>> tokenLists = loaderService.getTokenLists();
		
		List<WebPage> pages = new ArrayList<>();
		
		reLists.forEach(list -> {
				List<QueryBuilder> reQbs = buildRegexQuerys(list, webPage.getUrl());
				pages.addAll(reQbs.stream()
					.map(qb -> documentDao.filter(subId, "webpage", qb))
					.collect(Collectors.toList()));
			});
		
		tokenLists.forEach(toks -> {
			QueryBuilder qb = buildTermsQuery(toks, webPage.getUrl());
				pages.add(documentDao.filter(subId, "webpage", qb));
			});
		return checkResults(pages);
	}
	
	private Action checkResults(List<WebPage> pages) {
		Action action = Action.ALLOW;
		for (WebPage page : pages) {
			if (page.getUrl().equals("")) {
				action = Action.BLOCK;
				break;
			}
		}
		return action;
	}


	QueryBuilder buildTermsQuery(LexicalExpressionList<? extends TextEntity> tokenList, String url) {
		String[] terms = tokenList.getTokens();
		QueryBuilder qb = boolQuery()
				.mustNot(termsQuery("html", terms))
				.filter(termQuery("url", url));
		return qb;
	}

	List<QueryBuilder> buildRegexQuerys(LexicalExpressionList<? extends TextEntity> regexList, String url) {
		String[] regexs = regexList.getTokens();
		List<QueryBuilder> qbs = new ArrayList<>(); 
		
		Arrays.stream(regexs)
			.forEach(re -> {
				QueryBuilder qb = boolQuery()
					.mustNot(regexpQuery("html", re))
					.filter(termQuery("url", url));
				qbs.add(qb);
			});
		return qbs;
	}
}
