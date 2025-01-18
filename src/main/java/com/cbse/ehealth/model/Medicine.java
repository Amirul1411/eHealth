package com.cbse.ehealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name = "dosage (mg)")
	private Float dosage;

	private String expiry_date;

	public Medicine() {
	}

	public Medicine(String name, Float dosage, String expiry_date) {
		this.name = name;
		this.dosage = dosage;
		this.expiry_date = expiry_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getDosage() {
		return dosage;
	}

	public void setDosage(Float dosage) {
		this.dosage = dosage;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", dosage=" + dosage + ", expiry_date=" + expiry_date + "]";
	}

}
