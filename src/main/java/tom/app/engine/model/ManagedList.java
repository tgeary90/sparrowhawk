package tom.app.engine.model;

import java.util.Collections;
import java.util.List;

import java.util.UUID;

import jersey.repackaged.com.google.common.base.MoreObjects;

/**
 * List of PredefinedTextEntities. Read in from disk
 * as a Managed List
 * @author tomg
 *
 * @param <E>
 */
public class ManagedList {
	
	private final List<PredefinedTextEntity> managedList;
	private final String name;
	private long seq = 0L;
	

	public ManagedList(List<PredefinedTextEntity> managedList, String name) {
		super();
		this.managedList = managedList;
		this.name = name;
	}
	
	public List<PredefinedTextEntity> getEntities() {
		return Collections.unmodifiableList(managedList);
	}
	
	public void add(String token) {
		managedList.add(new PredefinedTextEntity(UUID.randomUUID(), token + ++seq, token));
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("managedlist", name).toString();
	}
}
