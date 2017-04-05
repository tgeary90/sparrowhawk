package tom.app.engine.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity<T> {
	
	protected UUID id;
	protected T value;
	
	public Entity(UUID id, T value) {
		super();
		this.id = id;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (! (o instanceof Entity)) {
			return false;
		}
		final Entity other = (Entity) o;
		return Objects.equals(this.id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
