package tom.app.engine.model;

import java.util.Objects;
import java.util.UUID;

public abstract class TextEntity extends Entity<String> {

	protected final String name;
	
	public TextEntity(UUID id, String name, String value) {
		super(id, value);
		this.name = name;
	}
	
	public abstract boolean match(String other);

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TextEntity)) {
			return false;
		}
		final TextEntity other = (TextEntity) o;
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.id,  other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
