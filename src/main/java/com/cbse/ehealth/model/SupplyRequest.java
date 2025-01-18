package com.cbse.ehealth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "supply_request")
public class SupplyRequest {

	public static final String STATUS_PENDING = "Pending Approval";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long staff_id;

	private Long item_id;

	private Long medicine_id;

	private String quantity_requested;

	private String request_date;

	private String status;

	private Long admin_id;

	public SupplyRequest() {
	}

	public SupplyRequest(Long staff_id, Long item_id, Long medicine_id, String quantity_requested, String request_date,
			String status, Long admin_id) {
		super();
		this.staff_id = staff_id;
		this.item_id = item_id;
		this.medicine_id = medicine_id;
		this.quantity_requested = quantity_requested;
		this.request_date = request_date;
		this.status = status;
		this.admin_id = admin_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(Long staff_id) {
		this.staff_id = staff_id;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public Long getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(Long medicine_id) {
		this.medicine_id = medicine_id;
	}

	public String getQuantity_requested() {
		return quantity_requested;
	}

	public void setQuantity_requested(String quantity_requested) {
		this.quantity_requested = quantity_requested;
	}

	public String getRequest_date() {
		return request_date;
	}

	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}

	@Override
	public String toString() {
		return "SupplyRequest [id=" + id + ", staff_id=" + staff_id + ", item_id=" + item_id + ", medicine_id="
				+ medicine_id + ", quantity_requested=" + quantity_requested + ", request_date=" + request_date
				+ ", status=" + status + ", admin_id=" + admin_id + "]";
	}

}
