package tom.app.engine.model;

import java.util.ArrayList;
import java.util.List;

import jersey.repackaged.com.google.common.base.MoreObjects;

/**
 * List of Text Entities (Token or Regex). Can be predefined or custom
 * @author tomg
 *
 */
public class LexicalExpressionList<E extends TextEntity>{

	private final List<E> lexList;
	private final String name;

	public LexicalExpressionList(List<E> lexList, String name) {
		super();
		this.lexList = lexList;
		this.name = name;
	}
	
	public String[] getTokens() {
		String[] tokens = new String[lexList.size()];
		List<String> tokenStrings = new ArrayList<>();
		lexList.forEach((te) -> tokenStrings.add(te.value));
		tokenStrings.toArray(tokens);
		return tokens;
	}
	
	public LexicalExpressionList<E> add(E e) {
		lexList.add(e);
		return this;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("lexlist", name).toString();
	}
	
	public int size() {
		return lexList.size();
	}
}
