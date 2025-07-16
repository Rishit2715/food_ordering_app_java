package com.tss.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.tss.service.OrderService;

public class OrderFacade {
    private OrderService orderService = new OrderService();

    private BaseDiscount base = new BaseDiscount();
    private FixedDiscountDecorator fixed = new FixedDiscountDecorator(base);
    private OccasionalDiscountDecorator occasional = new OccasionalDiscountDecorator(fixed, 100, LocalDate.of(2025, 7, 15));

    private IDiscount discountStrategy = occasional;

    public void updateFixedDiscount(double amount, double threshold) {
        fixed.setFixedDiscount(amount);
        fixed.setThreshold(threshold);
        System.out.println("✅ Fixed discount updated.");
    }

    public void updateOccasionalDiscount(double amount, LocalDate date) {
        occasional.setExtraDiscount(amount);
        occasional.setOccasionDate(date);
        System.out.println("✅ Occasional discount updated.");
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
			System.out.println("✅ Item added to cart.");
		} else {
			System.out.println("❌ Item not found.");
		}
	}

	public void viewCart() {
		orderService.getOrder().displayCart();
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void checkout(String name, IPayment payment) {
		Order order = orderService.getOrder();
		order.setCustomerName(name);

		double subtotal = order.getTotalBeforeDiscount();
		double discount = discountStrategy.getDiscount(subtotal);
		double finalTotal = subtotal - discount;

		String deliveryPartner = getRandomPartner();

		System.out.println("\n🧾 Invoice:");
		for (OrderItem item : order.getItems()) {
			System.out.println(item);
		}
		System.out.println("Subtotal: ₹" + subtotal);
		System.out.println("Discount: ₹" + discount);
		System.out.println("Total Payable: ₹" + finalTotal);
		System.out.println("Delivery Partner: " + deliveryPartner);

		if (payment.validate()) {
			payment.payment();
		} else {
			System.out.println("❌ Payment failed. Please try again.");
		}
	}

	private String getRandomPartner() {
		List<String> partners = List.of("Swiggy", "Zomato");
		return partners.get(new Random().nextInt(partners.size()));
	}
	
	public void displayDiscountSettings() {
	    if (discountStrategy instanceof OccasionalDiscountDecorator od) {
	        System.out.println("Occasional Discount: ₹" + od.getExtraDiscount() + " on " + od.getOccasionDate());
	        IDiscount inner = od.getBase();

	        if (inner instanceof FixedDiscountDecorator fd) {
	            System.out.println("Fixed Discount: ₹" + fd.getFixedDiscount() + " if total > ₹" + fd.getThreshold());
	        }
	    } else if (discountStrategy instanceof FixedDiscountDecorator fd) {
	        System.out.println("Fixed Discount: ₹" + fd.getFixedDiscount() + " if total > ₹" + fd.getThreshold());
	    } else {
	        System.out.println("No additional discounts configured.");
	    }
	}


}
