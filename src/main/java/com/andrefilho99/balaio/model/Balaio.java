package com.andrefilho99.balaio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.andrefilho99.balaio.utils.MessageUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "BALAIO")
public class Balaio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BALAIO_ID")
	private Integer balaioId;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@JsonIgnoreProperties(value = {"validated", "balaiosSent", "balaiosReceived"})
	@ManyToOne
	@JoinColumn(name = "USER_ID_FROM")
	private User from;
	
	@JsonIgnoreProperties(value = {"validated", "balaiosSent", "balaiosReceived"})
	@ManyToOne
	@JoinColumn(name = "USER_ID_TO")
	private User to;
	
	@Column(name = "LATITUDE")
	private double latitude;
	
	@Column(name = "LONGITUDE")
	private double longitude;
	
	@Column(name = "FOUND")
	private Boolean found;

	public Balaio() {
		found = false;
	}
	
	public Integer getBalaioId() {
		return balaioId;
	}

	public void setBalaioId(Integer balaioId) {
		this.balaioId = balaioId;
	}
	
	public String getMessage() {
		MessageUtils messageUtils = new MessageUtils();
		return messageUtils.decode(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Boolean getFound() {
		return found;
	}

	public void setFound(Boolean found) {
		this.found = found;
	}
}
