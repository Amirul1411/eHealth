package com.cbse.ehealth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String item_name;

	private int quantity;

	private String expiry_date;

	public Item() {
	}

	public Item(String item_name, int quantity, String expiry_date) {
		this.item_name = item_name;
		this.quantity = quantity;
		this.expiry_date = expiry_date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", item_name=" + item_name + ", quantity=" + quantity + ", expiry_date=" + expiry_date
				+ "]";
	}

}
