package com.tss.model;

import java.io.Serializable;

public class MenuItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private double price;
	private String description;

	public MenuItem(int id, String name, double price, String description) {
		this.id = id;
		this.name = name.trim();
		this.price = price;
		this.description = description.trim();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}

}
