package tom.app.engine.model;

import java.util.UUID;

public class Subscriber {
	private final UUID id;
	private final String name;
	private final License license;
	
	public Subscriber(UUID id, String name, License license) {
		super();
		this.id = id;
		this.name = name;
		this.license = license;
	}

	public enum License { TRIAL, CUSTOMER }

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public License getLicense() {
		return license;
	};
}
