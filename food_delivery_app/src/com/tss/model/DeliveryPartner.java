package com.tss.model;

import java.io.Serializable;
import java.util.Objects;

public class DeliveryPartner implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String phone;

	public DeliveryPartner(int id, String name, String phone) {
		this.id = id;
		this.name = name.trim();
		this.phone = phone == null ? "" : phone.trim();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public String toString() {
		return "DeliveryPartner{id=" + id + ", name='" + name + "', phone='" + phone + "'}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DeliveryPartner dp))
			return false;
		return id == dp.id || name.equalsIgnoreCase(dp.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name.toLowerCase());
	}
}
