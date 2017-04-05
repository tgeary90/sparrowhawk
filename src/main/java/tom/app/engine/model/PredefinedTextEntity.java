package tom.app.engine.model;

import java.util.UUID;

/**
 * Class represents a String text entity (token)
 * @author tomg
 *
 */
public class PredefinedTextEntity extends TextEntity {

	public PredefinedTextEntity(UUID id, String name, String value) {
		super(id, name, value);
	}

	@Override
	public boolean match(String other) {
		return other.contains(value);
	}
}
