package com.cbse.ehealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

	public final static String STATUS_PAID = "Paid";
	public final static String STATUS_PENDING = "Pending";
	public final static String STATUS_UNPAID = "Unpaid";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long patient_id;

	@Column(name = "amount (RM)")
	private Float amount;

	private String date_issued;

	private String status;

	public Invoice() {
	}

	public Invoice(Long patient_id, Float amount, String date_issued, String status) {
		this.patient_id = patient_id;
		this.amount = amount;
		this.date_issued = date_issued;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getDate_issued() {
		return date_issued;
	}

	public void setDate_issued(String date_issued) {
		this.date_issued = date_issued;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", patient_id=" + patient_id + ", amount=" + amount + ", date_issued="
				+ date_issued + ", status=" + status + "]";
	}

}
