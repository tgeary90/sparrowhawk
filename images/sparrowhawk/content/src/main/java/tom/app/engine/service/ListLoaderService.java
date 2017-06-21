package tom.app.engine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import tom.app.engine.model.CustomTextEntity;
import tom.app.engine.model.LexicalExpressionList;
import tom.app.engine.model.ManagedList;
import tom.app.engine.model.PredefinedTextEntity;
import tom.app.engine.model.TextEntity;

@Component
public class ListLoaderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListLoaderService.class);
	
	private final LexicalExpressionList<TextEntity> swearingList;
	private final LexicalExpressionList<TextEntity> gunsList;
	private final LexicalExpressionList<CustomTextEntity> lexList;
	
	public ListLoaderService() {
		this.swearingList = new LexicalExpressionList<>(new ArrayList<>(10), "swearing");
		this.gunsList = new LexicalExpressionList<>(new ArrayList<>(10), "guns");
		this.lexList = new LexicalExpressionList<>(new ArrayList<>(10), "swearslexlist");
		
		load();
	}
	
	public void load() {
		readIntoList("general-swear.txt", swearingList);
		readIntoList("firearms.txt", gunsList);
		generateList();
		
	}

	public List<LexicalExpressionList<? extends TextEntity>> getTokenLists() {
		List<LexicalExpressionList<? extends TextEntity>> lists = new ArrayList<>();
		lists.add(gunsList);
		lists.add(swearingList);
		return lists;
	}
	

	public List<LexicalExpressionList<? extends TextEntity>> getRegexLists() {
		List<LexicalExpressionList<? extends TextEntity>> lists = new ArrayList<>();
		lists.add(lexList);
		return lists;
	}
	
	private void generateList() {
		lexList
			.add(new CustomTextEntity(UUID.randomUUID(), "bstrd", "b[a-zA-Z]st[a-zA-Z]rd"))
			.add(new CustomTextEntity(UUID.randomUUID(), "fk", "f[a-zA-Z]ck"));
	}

	private void readIntoList(String tokens, LexicalExpressionList<TextEntity> list) {
		InputStream is = getClass().getResourceAsStream("/scanning/managedlists/" + tokens);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		try {
			String line = buf.readLine();
			int i = 0;
			while (line != null) {
				list.add(new PredefinedTextEntity(UUID.randomUUID(), String.valueOf(++i), line));
				LOGGER.info("Added {} to {}", line, list);
				line = buf.readLine();
			}
		}
		catch (IOException e) {
			LOGGER.error("Could not read into {}", list.toString(), e);
			System.exit(1);
		}
	}
}
