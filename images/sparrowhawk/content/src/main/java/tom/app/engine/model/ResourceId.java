package tom.app.engine.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ResourceId {
	
	private int id;

	@JsonCreator
	public ResourceId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
