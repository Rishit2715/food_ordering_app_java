package com.tss.service;

import java.util.List;
import java.util.Scanner;

import com.tss.model.IPayment;
import com.tss.model.OrderFacade;
import com.tss.model.OrderItem;
import com.tss.model.User;

public class CustomerService {

	public static void handleCustomer(Scanner scanner, User user) {
		OrderFacade facade = new OrderFacade();
		PaymentService paymentService = new PaymentService();

		while (true) {
			System.out.println("\nPlease choose an option:");
			System.out.println("1. Browse Italian Menu");
			System.out.println("2. Browse Indian Menu");
			System.out.println("3. Browse Chinese Menu");
			System.out.println("4. View Cart");
			System.out.println("5. Remove Item from Cart");
			System.out.println("6. Proceed to Checkout");
			System.out.println("7. Logout");
			System.out.print("Enter your choice: ");

			int option;
			try {
				option = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("❌ Invalid input. Please enter a number.");
				continue;
			}

			switch (option) {

			case 1:
			case 2:
			case 3: {
				String cuisine = getCuisineForOption(option);
				if (cuisine == null) {
					System.out.println("Invalid cuisine option.");
					break;
				}

				facade.showMenu(cuisine);

				System.out.print("Enter item ID to add to cart (or 0 to go back): ");
				int itemId;
				try {
					itemId = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid ID.");
					break;
				}
				if (itemId == 0) {
					break;
				}

				System.out.print("Enter quantity: ");
				int qty;
				try {
					qty = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid quantity.");
					break;
				}

				if (qty <= 0) {
					System.out.println("Quantity must be > 0.");
					break;
				}

				facade.addItem(cuisine, itemId, qty);
				break;
			}

			case 4:
				printCartBox(facade);
				break;

			case 5: {
				List<OrderItem> orderItems = facade.getOrderService().getOrder().getItems();
				if (orderItems.isEmpty()) {
					System.out.println("Your cart is empty.");
					break;
				}
				int count = printCartBox(facade);
				System.out.print("Enter line number to remove: ");
				int lineNo;
				try {
					lineNo = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("Invalid input.");
					break;
				}
				if (lineNo < 1 || lineNo > count) {
					System.out.println("No such line.");
					break;
				}
				OrderItem removedItem = orderItems.remove(lineNo - 1);
				System.out.println("Removed: " + removedItem.getItem().getName());
				break;
			}

			case 6: {
				boolean cartEmpty = facade.getOrderService().getOrder().getItems().isEmpty();
				double subtotal = facade.getOrderService().getOrder().getTotalBeforeDiscount();
				if (cartEmpty || subtotal <= 0) {
					System.out.println("Your cart is empty. Add items before checkout.");
					break;
				}

				String name = user.getUsername();
				System.out.println("Logged in as: " + name);
				System.out.printf("Subtotal payable: ₹%.2f%n", subtotal);

				System.out.println("\n--- Payment ---");
				System.out.println("Select a payment method (or 0 to cancel):");
				IPayment payment = paymentService.collectPayment(scanner, (int) subtotal);

				if (payment == null) {
					System.out.println("Checkout cancelled. Select option 6 again when you're ready.");
					break;
				}

				try {
					facade.checkout(name, payment);
					System.out.println("Order placed successfully! Thank you.");
					return; 
				} catch (Exception e) {
					System.out.println("Checkout failed: " + e.getMessage());
					System.out.println("You can try again by selecting option 6 (Proceed to Checkout).");
					break;
				}
			}

			// ---- LOGOUT ----
			case 7:
				System.out.println("Logged out. Returning to main menu...");
				return;

			// ---- INVALID ----
			default:
				System.out.println("Invalid option.");
				break;
			}
		}
	}

	private static String getCuisineForOption(int option) {
		switch (option) {
		case 1:
			return "italian";
		case 2:
			return "indian";
		case 3:
			return "chinese";
		default:
			return null;
		}
	}

	/**
	 * Print cart in a box format with borders.
	 * 
	 * @return number of items printed
	 */
	private static int printCartBox(OrderFacade facade) {
		List<OrderItem> items = facade.getOrderService().getOrder().getItems();
		if (items.isEmpty()) {
			System.out.println("Your cart is empty.");
			return 0;
		}

		System.out.println("\n+----------------------------------------------------------------+");
		System.out.println("|                           YOUR CART                           |");
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("| %-4s | %-5s | %-15s | %-7s | %-8s |\n", "No.", "ID", "Item", "Qty", "Line ₹");
		System.out.println("+----------------------------------------------------------------+");

		int i = 1;
		for (OrderItem cartItem : items) {
			int id = cartItem.getItem().getId();
			String nm = cartItem.getItem().getName();
			int qty = cartItem.getQuantity();
			double lineTotal = cartItem.getItem().getPrice() * qty;
			System.out.printf("| %-4d | %-5d | %-15s | %-7d | %-8.2f |\n", i, id, nm, qty, lineTotal);
			i++;
		}

		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("| %-36s %-10.2f |\n", "Subtotal:",
				facade.getOrderService().getOrder().getTotalBeforeDiscount());
		System.out.println("+----------------------------------------------------------------+\n");
		return i - 1;
	}
}
