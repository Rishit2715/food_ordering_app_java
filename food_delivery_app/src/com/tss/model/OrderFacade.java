package com.tss.model;

import java.time.LocalDate;

import com.tss.exception.DeliveryPartnerMustNotBeEmptyException;
import com.tss.model.menu.IMenu;
import com.tss.model.menu.MenuFactory;
import com.tss.model.menu.MenuItem;
import com.tss.model.payment.IPayment;
import com.tss.service.DeliveryPartnerService;
import com.tss.service.OrderService;

public class OrderFacade {
	private final OrderService orderService = new OrderService();

	private final BaseDiscount base = new BaseDiscount();
	private final FixedDiscountDecorator fixed = new FixedDiscountDecorator(base);
	private final OccasionalDiscountDecorator occasional = new OccasionalDiscountDecorator(fixed, 100,
			LocalDate.of(2025, 7, 15));
	private final IDiscount discountStrategy = occasional;

	private final DeliveryPartnerService deliveryPartnerService;

	public OrderFacade() {
		this(new DeliveryPartnerService());
	}

	public OrderFacade(DeliveryPartnerService deliveryPartnerService) {
		this.deliveryPartnerService = deliveryPartnerService;
	}

	public void updateFixedDiscount(double amount, double threshold) {
		fixed.setFixedDiscount(amount);
		fixed.setThreshold(threshold);
		System.out.println("Fixed discount updated.");
	}

	public void updateOccasionalDiscount(double amount, LocalDate date) {
		occasional.setExtraDiscount(amount);
		occasional.setOccasionDate(date);
		System.out.println("Occasional discount updated.");
	}

	public void showMenu(String cuisine) {
		IMenu menu = MenuFactory.getMenu(cuisine);
		menu.displayMenu();
	}

	public void addItem(String cuisine, int itemId, int quantity) {
		IMenu menu = MenuFactory.getMenu(cuisine);
		MenuItem item = menu.getItems().stream().filter(i -> i.getId() == itemId).findFirst().orElse(null);
		if (item != null) {
			orderService.addItemToOrder(item, quantity);
			System.out.println("Item added to cart.");
		} else {
			System.out.println("Item not found.");
		}
	}

	public void viewCart() {
		orderService.getOrder().displayCart();
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public DeliveryPartnerService getDeliveryPartnerService() {
		return deliveryPartnerService;
	}

	public void checkout(String name, IPayment payment) {
		Order order = orderService.getOrder();
		order.setCustomerName(name);

		double subtotal = order.getTotalBeforeDiscount();
		double discount = discountStrategy.getDiscount(subtotal);
		double finalTotal = subtotal - discount;

		DeliveryPartner partner = deliveryPartnerService.getRandomPartner()
				.orElseThrow(DeliveryPartnerMustNotBeEmptyException::new);

		System.out.println("\n+------------------------------------------------------------+");
		System.out.println("|                          INVOICE                          |");
		System.out.println("+------------------------------------------------------------+");
		System.out.printf("| %-20s | %-7s | %-8s | %-10s |\n", "Item", "Qty", "Price ₹", "Line ₹");
		System.out.println("+------------------------------------------------------------+");

		for (OrderItem item : order.getItems()) {
			String nameItem = item.getItem().getName();
			int qty = item.getQuantity();
			double price = item.getItem().getPrice();
			double lineTotal = price * qty;

			System.out.printf("| %-20s | %-7d | %-8.2f | %-10.2f |\n", nameItem, qty, price, lineTotal);
		}

		System.out.println("+------------------------------------------------------------+");
		System.out.printf("| %-38s | %-10.2f |\n", "Subtotal:", subtotal);
		System.out.printf("| %-38s | %-10.2f |\n", "Discount:", discount);
		System.out.printf("| %-38s | %-10.2f |\n", "Total Payable:", finalTotal);
		System.out.println("+------------------------------------------------------------+");
		System.out.printf("| %-38s | %-10s |\n", "Delivery Partner:", partner.getName());
		System.out.println("+------------------------------------------------------------+\n");

		if (payment.validate()) {
			payment.payment();
		} else {
			System.out.println("❌ Payment failed. Please try again.");
		}
	}

	public void displayDiscountSettings() {
		System.out.println("\n------ Discount Settings ------");
		System.out.printf("%-20s | %-15s | %-20s%n", "Discount Type", "Amount", "Condition");
		System.out.println("---------------------------------------------------------------");

		if (discountStrategy instanceof OccasionalDiscountDecorator od) {
			System.out.printf("%-20s | ₹%-14.2f | On: %s%n", "Occasional Discount", od.getExtraDiscount(),
					od.getOccasionDate());

			IDiscount inner = od.getBase();
			if (inner instanceof FixedDiscountDecorator fd) {
				System.out.printf("%-20s | ₹%-14.2f | If Total > ₹%.2f%n", "Fixed Discount", fd.getFixedDiscount(),
						fd.getThreshold());
			}
		} else if (discountStrategy instanceof FixedDiscountDecorator fd) {
			System.out.printf("%-20s | ₹%-14.2f | If Total > ₹%.2f%n", "Fixed Discount", fd.getFixedDiscount(),
					fd.getThreshold());
		} else {
			System.out.println("No additional discounts configured.");
		}
	}

	public double calculateDiscount() {
		return discountStrategy.getDiscount(orderService.getOrder().getTotalBeforeDiscount());
	}

}
