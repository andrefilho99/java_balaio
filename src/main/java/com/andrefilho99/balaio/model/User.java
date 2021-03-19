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
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@OneToMany(mappedBy = "from")
	private List<Contact> contacts;
	
	@JsonIgnoreProperties(value = {"from"})
	@OneToMany(mappedBy = "from")
	private List<Balaio> balaiosSent;
	
	@JsonIgnoreProperties(value = {"to"})
	@OneToMany(mappedBy = "to")
	private List<Balaio> balaiosReceived;
	
	public User() {
		contacts = new ArrayList<Contact>();
		balaiosSent = new ArrayList<Balaio>();
		balaiosReceived = new ArrayList<Balaio>();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
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

	public void setBalaiosReceived(List<Balaio> balaiosReceived) {
		this.balaiosReceived = balaiosReceived;
	}
}
