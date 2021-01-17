package core.domain.dto;

import java.time.LocalDateTime;

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

	public ManifestationsSearchParamethers() {
		super();
	}

	public ManifestationsSearchParamethers(String name, LocalDateTime dateFrom, LocalDateTime dateTo, String city,
			int priceFrom, int priceTo, String type, boolean onlyNotSolved) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.city = city;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.type = type;
		this.onlyNotSolved = onlyNotSolved;
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

	public void setManifestationType(String type) {
		this.type = type;
	}

	public boolean isOnlyNotSolved() {
		return onlyNotSolved;
	}

	public void setOnlyNotSolved(boolean onlyNotSolved) {
		this.onlyNotSolved = onlyNotSolved;
	}

}
