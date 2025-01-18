package com.cbse.ehealth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shift")
public class Shift {

	public final static String SHIFT_MORNING = "Morning";
	public final static String SHIFT_EVENING = "Evening";
	public final static String SHIFT_NIGHT = "Night";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long staffId;

	private String shift_time;

	private String date;

	public Shift() {
	}

	public Shift(Long staff_id, String shift_time, String date) {
		this.staffId = staff_id;
		this.shift_time = shift_time;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStaff_id() {
		return staffId;
	}

	public void setStaff_id(Long staff_id) {
		this.staffId = staff_id;
	}

	public String getShift_time() {
		return shift_time;
	}

	public void setShift_time(String shift_time) {
		this.shift_time = shift_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Shift [id=" + id + ", staff_id=" + staffId + ", shift_time=" + shift_time + ", date=" + date + "]";
	}

}
