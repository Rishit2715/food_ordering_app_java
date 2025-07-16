package com.tss.service;

import java.util.Scanner;

import com.tss.model.IPayment;
import com.tss.model.OrderFacade;
import com.tss.model.User;

public class CustomerService {
	public static void handleCustomer(Scanner scanner, User user){
        OrderFacade facade = new OrderFacade();
        PaymentService paymentService = new PaymentService();

        while (true) {
            System.out.println("\n🍽️ Select Cuisine to Order From:");
            System.out.println("1. Italian");
            System.out.println("2. Indian");
            System.out.println("3. Chinese");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1, 2, 3 -> {
                    String cuisine = switch (option) {
                        case 1 -> "italian";
                        case 2 -> "indian";
                        case 3 -> "chinese";
                        default -> "";
                    };

                    facade.showMenu(cuisine);

                    System.out.print("Enter item ID to add to cart (or 0 to go back): ");
                    int itemId = scanner.nextInt();
                    if (itemId == 0) break;

                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();

                    facade.addItem(cuisine, itemId, qty);
                }

                case 4 -> facade.viewCart();

                case 5 -> {
                    String name = user.getUsername(); 
                    System.out.println("👤 Logged in as: " + name);

                    double subtotal = facade.getOrderService().getOrder().getTotalBeforeDiscount();
                    IPayment payment = paymentService.collectPayment(scanner, (int) subtotal);

                    if (payment != null) {
                        facade.checkout(name, payment);
                        return;
                    } else {
                        System.out.println("❌ Payment method not selected. Try again.");
                    }
                }

                case 6 -> {
                    System.out.println("Thank you for visiting!");
                    return;
                }

                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
}
