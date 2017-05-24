package tom.app.engine.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for Subscriber details.
 * @author tomg
 *
 */
public class Subscriber {
	private final UUID id;
	private final String subscriberName;
	private final License license;
	
	public Subscriber(
			@JsonProperty("id") UUID id, 
			@JsonProperty("name") String name, 
			@JsonProperty("license") License license) {
		this.id = id;
		this.subscriberName = name;
		this.license = license;
	}

	public enum License { TRIAL, CUSTOMER }

	public UUID getId() {
		return id;
	}

	public String getName() {
		return subscriberName;
	}

	public License getLicense() {
		return license;
	};
}
