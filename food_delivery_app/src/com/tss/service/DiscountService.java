package com.tss.service;

import java.time.LocalDate;
import java.util.Scanner;

import com.tss.model.OrderFacade;

public class DiscountService {
    public static void manageDiscounts(OrderFacade facade, Scanner scanner) {
        while (true) {
            System.out.println("\n🎁 Manage Discounts:");
            System.out.println("1. Update Fixed Discount");
            System.out.println("2. Update Occasional Discount");
            System.out.println("3. View Current Discounts");
            System.out.println("4. Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new fixed discount amount: ");
                    double amt = scanner.nextDouble();
                    System.out.print("Enter new threshold (e.g. 500): ");
                    double threshold = scanner.nextDouble();
                    scanner.nextLine();
                    facade.updateFixedDiscount(amt, threshold);
                }
                case 2 -> {
                    System.out.print("Enter new occasional discount amount: ");
                    double occAmt = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateStr);
                    facade.updateOccasionalDiscount(occAmt, date);
                }
                case 3 -> {
                    System.out.println("\n📊 Current Discount Settings:");
                    facade.displayDiscountSettings(); // ✅ This method must be implemented in OrderFacade
                }
                case 4 -> {
                    System.out.println("🔙 Returning to Admin Menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }
}
