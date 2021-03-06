package core.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public abstract class BaseEntity {
	private UUID id;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime createdAt;
	private boolean active;
	
	public BaseEntity()
	{
		this.id = UUID.randomUUID();
		this.createdAt = LocalDateTime.now();
		this.active = true;
	}

	public BaseEntity(UUID id, LocalDateTime createdAt, boolean active) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.active = active;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
