package core.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class ManifestationsSearchParamethers {
	private String name;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime dateFrom;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime dateTo;
	private String city;
	private int priceFrom;
	private int priceTo;

	private String type;
	private boolean onlyNotSolved;

	private String sortBy;
	private String orderBy;
	private UUID sellerId;
	private String statusSetting;
	private boolean status;

	public ManifestationsSearchParamethers() {
		super();
	}

	public ManifestationsSearchParamethers(String name, LocalDateTime dateFrom, LocalDateTime dateTo, String city,
			int priceFrom, int priceTo, String type, boolean onlyNotSolved, String sortBy, String orderBy,
			UUID sellerId, String statusSetting, boolean status) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.city = city;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.type = type;
		this.onlyNotSolved = onlyNotSolved;
		this.sortBy = sortBy;
		this.orderBy = orderBy;
		this.sellerId = sellerId;
		this.statusSetting = statusSetting;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isOnlyNotSolved() {
		return onlyNotSolved;
	}

	public void setOnlyNotSolved(boolean onlyNotSolved) {
		this.onlyNotSolved = onlyNotSolved;
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

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}

	public String getStatusSetting() {
		return statusSetting;
	}

	public void setStatusSetting(String statusSetting) {
		this.statusSetting = statusSetting;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
