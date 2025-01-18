package com.cbse.ehealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payroll")
public class Payroll {

	public final static String STATUS_PAID = "Paid";
	public final static String STATUS_PENDING = "Pending";
	public final static String STATUS_UNPAID = "Unpaid";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long staffId;

	@Column(name = "salary (RM)")
	private Float salary;

	@Column(name = "bonus (RM)")
	private Float bonus;

	private String status;

	public Payroll() {
	}

	public Payroll(Long staff_id, Float salary, Float bonus, String status) {
		this.staffId = staff_id;
		this.salary = salary;
		this.bonus = bonus;
		this.status = status;
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

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Float getBonus() {
		return bonus;
	}

	public void setBonus(Float bonus) {
		this.bonus = bonus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Payroll [id=" + id + ", staff_id=" + staffId + ", salary=" + salary + ", bonus=" + bonus + ", status="
				+ status + "]";
	}

}
