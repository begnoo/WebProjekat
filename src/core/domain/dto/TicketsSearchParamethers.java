package core.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class TicketsSearchParamethers {
	private UUID buyerId;
	private String manifestationName;
	private int priceFrom;
	private int priceTo;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime dateFrom;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime dateTo;
	
	private UUID manifestationId;

	private String type;
	private String status;

	private String sortBy;
	private String orderBy;

	public TicketsSearchParamethers() {
		super();
	}

	public TicketsSearchParamethers(UUID buyerId, String manifestationName, int priceFrom, int priceTo,
			LocalDateTime dateFrom, LocalDateTime dateTo, String type, String status, String sortBy, String orderBy, UUID manifestationId) {
		super();
		this.buyerId = buyerId;
		this.manifestationName = manifestationName;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.type = type;
		this.status = status;
		this.sortBy = sortBy;
		this.orderBy = orderBy;
		this.manifestationId = manifestationId;
	}

	public UUID getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}

	public String getManifestationName() {
		return manifestationName;
	}

	public void setManifestationName(String manifestationName) {
		this.manifestationName = manifestationName;
	}

	public int getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(int priceFrom) {
		this.priceFrom = priceFrom;
	}

	public int getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(int priceTo) {
		this.priceTo = priceTo;
	}

	public LocalDateTime getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDateTime dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDateTime getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDateTime dateTo) {
		this.dateTo = dateTo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
	}

}
