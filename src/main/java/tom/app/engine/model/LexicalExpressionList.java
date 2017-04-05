package tom.app.engine.model;

import java.util.Collections;
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
	
	public List<E> getEntities() {
		return Collections.unmodifiableList(lexList);
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
}
