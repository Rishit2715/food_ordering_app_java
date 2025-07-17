package com.tss.service;

import java.util.Scanner;

import com.tss.exception.DeliveryPartnerLimitExceededException;
import com.tss.exception.DeliveryPartnerMustNotBeEmptyException;
import com.tss.model.DeliveryPartner;
import com.tss.model.IMenu;
import com.tss.model.MenuFactory;
import com.tss.model.MenuItem;
import com.tss.model.OrderFacade;

public class AdminService {

    public static void handleAdmin(Scanner scanner) {
        if (!adminLogin(scanner)) {
            System.out.println("Login failed. Exiting...");
            return;
        }

        OrderFacade orderFacade = new OrderFacade();
        DeliveryPartnerService deliverySvc = orderFacade.getDeliveryPartnerService();

        while (true) {
            System.out.println("\n Admin Panel:");
            System.out.println("1. Manage Menu");
            System.out.println("2. Manage Discounts");
            System.out.println("3. Manage Delivery Partners");
            System.out.println("4. Logout");

            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1 -> manageMenu(scanner);
                case 2 -> DiscountService.manageDiscounts(orderFacade, scanner);
                case 3 -> manageDeliveryPartners(scanner, deliverySvc);
                case 4 -> {
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static boolean adminLogin(Scanner scanner) {
        System.out.println("\nAdmin Login");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return username.equals("rishit") && password.equals("rishit123");
    }

    private static void manageDeliveryPartners(Scanner scanner, DeliveryPartnerService svc) {
        while (true) {
            System.out.println("\n Manage Delivery Partners:");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. View");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");
            int ch = scanner.nextInt();
            scanner.nextLine();

            switch (ch) {
                case 1 -> {
                    System.out.print("Enter Partner ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Partner Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Partner Phone (optional): ");
                    String phone = scanner.nextLine();
                    try {
                        svc.addPartner(new DeliveryPartner(id, name, phone));
                    } catch (DeliveryPartnerLimitExceededException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    svc.listPartners();
                    System.out.print("Enter Partner ID to remove: ");
                    int rid = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        if (svc.removePartnerById(rid)) {
                            System.out.println("Partner removed.");
                        } else {
                            System.out.println("Partner ID not found.");
                        }
                    } catch (DeliveryPartnerMustNotBeEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> svc.listPartners();
                case 4 -> {
                    System.out.println("Returning to Admin Panel...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageMenu(Scanner scanner) {
        System.out.println("\nChoose a menu to manage:");
        System.out.println("1. Italian");
        System.out.println("2. Indian");
        System.out.println("3. Chinese");
        System.out.println("4. Return to Admin Panel");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 4) {
            System.out.println("Returning to Admin Panel...");
            return;
        }

        String menuType = switch (choice) {
            case 1 -> "italian";
            case 2 -> "indian";
            case 3 -> "chinese";
            default -> null;
        };

        if (menuType == null) {
            System.out.println("Invalid menu choice!");
            return;
        }

        IMenu menu = MenuFactory.getMenu(menuType);
        MenuService service = new MenuService(menu);

        while (true) {
            System.out.println("\nMenu Management (" + menuType.toUpperCase() + "):");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Edit Item");
            System.out.println("4. Display Menu");
            System.out.println("5. Return to Manage Menu");
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
                    System.out.println("Current Menu Items:");
                    service.displayMenu();
                    System.out.print("Enter ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    menu.removeItem(removeId);
                    System.out.println("Item removed successfully (if it existed).");
                }
                case 3 -> {
                    System.out.println("Current Menu Items:");
                    service.displayMenu();
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
                    System.out.println("Returning to Manage Menu...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
