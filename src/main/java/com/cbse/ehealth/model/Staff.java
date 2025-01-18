package com.cbse.ehealth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {

	public final static String ROLE_STAFF = "Staff";
	public final static String ROLE_DOCTOR = "Doctor";
	public final static String ROLE_NURSE = "Nurse";

	public final static String DEPARTMENT_CARDIOLOGY = "Cardiology";
	public final static String DEPARTMENT_NEUROLOGY = "Neurology";
	public final static String DEPARTMENT_PEDIATRICS = "Pediatrics";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String role;

	private String department;

	private String contact;

	public Staff() {
	}

	public Staff(String username, String password, String role, String department, String contact) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.department = department;
		this.contact = contact;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", department=" + department + ", contact=" + contact + "]";
	}

}
