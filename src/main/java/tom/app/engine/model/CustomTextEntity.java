package tom.app.engine.model;

import java.util.UUID;

/**
 * Class that represents a regex to match against.
 * @author tomg
 *
 */
public class CustomTextEntity extends TextEntity {

	public CustomTextEntity(UUID id, String name, String regex) {
		super(id, name, regex);
	}

	@Override
	public boolean match(String other) {
		return other.matches(value);
	}
}
