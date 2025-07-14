package com.tss.test;

import java.util.Scanner;

import com.tss.model.MenuFactory;
import com.tss.model.IMenu;
import com.tss.model.MenuItem;
import com.tss.service.MenuService;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Food Management System");
        System.out.println("Choose role:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");

        int role = scanner.nextInt();
        scanner.nextLine();

        if (role == 1) {
            if (!adminLogin(scanner)) {
                System.out.println("❌ Login failed. Exiting...");
                return;
            }

            while (true) {
                System.out.println("\n🔧 Admin Panel:");
                System.out.println("1. Manage Menu");
                System.out.println("2. Manage Discounts");
                System.out.println("3. Manage Delivery Partners");
                System.out.println("4. Logout");

                int adminChoice = scanner.nextInt();
                scanner.nextLine();

                switch (adminChoice) {
                    case 1 -> manageMenu(scanner);
                    case 2 -> {
                        System.out.println("📌 Manage Discounts feature coming soon!");
                        // Placeholder: manageDiscounts(scanner);
                    }
                    case 3 -> {
                        System.out.println("📌 Manage Delivery Partners feature coming soon!");
                        // Placeholder: manageDeliveryPartners(scanner);
                    }
                    case 4 -> {
                        System.out.println("👋 Logged out successfully.");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice.");
                }
            }

        } else if (role == 2) {
            System.out.println("👷‍♂️ Customer module not implemented yet.");
        } else {
            System.out.println("❌ Invalid role selected.");
        }
    }

    private static boolean adminLogin(Scanner scanner) {
        System.out.println("\n🔐 Admin Login");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return username.equals("admin") && password.equals("admin123");
    }

    private static void manageMenu(Scanner scanner) {
        System.out.println("\nChoose a menu to manage:");
        System.out.println("1. Italian");
        System.out.println("2. Indian");
        System.out.println("3. Chinese");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String menuType = switch (choice) {
            case 1 -> "italian";
            case 2 -> "indian";
            case 3 -> "chinese";
            default -> null;
        };

        if (menuType == null) {
            System.out.println("❌ Invalid menu choice!");
            return;
        }

        IMenu menu = MenuFactory.getMenu(menuType);
        MenuService service = new MenuService(menu);

        while (true) {
            System.out.println("\n📋 Menu Management (" + menuType.toUpperCase() + "):");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Edit Item");
            System.out.println("4. Display Menu");
            System.out.println("5. Back to Admin Panel");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();

                    service.addItem(new MenuItem(id, name, price, desc));
                }

                case 2 -> {
                    System.out.print("Enter ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    menu.removeItem(removeId);
                }

                case 3 -> {
                    System.out.print("Enter ID to edit: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();

                    service.editItem(new MenuItem(editId, newName, newPrice, newDesc));
                }

                case 4 -> service.displayMenu();

                case 5 -> {
                    System.out.println("🔙 Returning to Admin Panel...");
                    return;
                }

                default -> System.out.println("❌ Invalid option.");
            }
        }
    }
}
