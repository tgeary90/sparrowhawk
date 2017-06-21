package tom.app.engine.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tom.app.engine.model.Subscriber.License;

public abstract class SubscriberMixIn {

	@JsonCreator
	public SubscriberMixIn(
			@JsonProperty("id") UUID id,
			@JsonProperty("name") String name,
			@JsonProperty("license") License license) {}
	
	
}
