package com.cbse.ehealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {

	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_CONFIRMED = "Confirmed";
	public static final String STATUS_CANCELLED = "Cancelled";
	public static final String STATUS_RESCHEDULED = "Rescheduled";
	public static final String STATUS_FINISHED = "Finished";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long patient_id;

	private Long staffId;

	private String appointment_date;

	private String appointment_time;

	@Column(name = "appointment_duration (hours)")
	private float appointment_duration;

	private String status;

	public Appointment() {
	}

	public Appointment(Long patient_id, Long staff_id, String appointment_date, String appointment_time,
			float appointment_duration, String status) {
		this.patient_id = patient_id;
		this.staffId = staff_id;
		this.appointment_date = appointment_date;
		this.appointment_time = appointment_time;
		this.appointment_duration = appointment_duration;
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

	public Long getStaff_id() {
		return staffId;
	}

	public void setStaff_id(Long staff_id) {
		this.staffId = staff_id;
	}

	public String getAppointment_date() {
		return appointment_date;
	}

	public void setAppointment_date(String appointment_date) {
		this.appointment_date = appointment_date;
	}

	public String getAppointment_time() {
		return appointment_time;
	}

	public void setAppointment_time(String appointment_time) {
		this.appointment_time = appointment_time;
	}

	public float getAppointment_duration() {
		return appointment_duration;
	}

	public void setAppointment_duration(float appointment_duration) {
		this.appointment_duration = appointment_duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", patient_id=" + patient_id + ", staff_id=" + staffId + ", appointment_date="
				+ appointment_date + ", appointment_time=" + appointment_time + ", appointment_duration="
				+ appointment_duration + ", status=" + status + "]";
	}

}
