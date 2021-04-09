package com.andrefilho99.balaio.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "NICKNAME")
	private String nickname;
	
	@Column(name = "number")
	private String number;
	
	@JsonIgnoreProperties(value = {"from"})
	@OneToMany(mappedBy = "from")
	private List<Balaio> balaiosSent;
	
	@JsonIgnoreProperties(value = {"to"})
	@OneToMany(mappedBy = "to")
	private List<Balaio> balaiosReceived;
	
	@Column(name = "IS_VALIDATED")
	private boolean isValidated;
	
	public User() {
		balaiosSent = new ArrayList<Balaio>();
		balaiosReceived = new ArrayList<Balaio>();
		isValidated = false;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Balaio> getBalaiosSent() {
		return balaiosSent;
	}

	public void setBalaiosSent(List<Balaio> balaiosSent) {
		this.balaiosSent = balaiosSent;
	}

	public List<Balaio> getBalaiosReceived() {
		return balaiosReceived;
	}
	
	@JsonIgnore
	public List<Balaio> getBalaiosNotFound() {
		
		List<Balaio> balaiosNotFound = new ArrayList<Balaio>();
		
		for(Balaio balaio : balaiosReceived) {
			if(!balaio.getFound()) {
				balaiosNotFound.add(balaio);
			}
		}
		
		return balaiosNotFound;
	}

	public void setBalaiosReceived(List<Balaio> balaiosReceived) {
		this.balaiosReceived = balaiosReceived;
	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
}
