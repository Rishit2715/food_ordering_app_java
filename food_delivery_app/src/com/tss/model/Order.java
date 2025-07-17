package com.tss.model;

import java.util.ArrayList;
import java.util.List;

public class Order  {
	private List<OrderItem> items = new ArrayList<>();
	private String customerName;

	public void setCustomerName(String name) {
		this.customerName = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void addItem(MenuItem item, int quantity) {
		items.add(new OrderItem(item, quantity));
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public double getTotalBeforeDiscount() {
		return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
	}

	public void displayCart() {
		if (items.isEmpty()) {
			System.out.println("Your cart is empty.");
			return;
		}

		System.out.println("\nYour Cart:");
		for (OrderItem item : items) {
			System.out.printf("- %s x%d = ₹%.2f\n", item.getItem().getName(), item.getQuantity(), item.getSubtotal());
		}
	}
}
