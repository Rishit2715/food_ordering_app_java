package com.tss.test;

import java.util.Scanner;

import com.tss.model.User;
import com.tss.service.AdminService;
import com.tss.service.AuthService;
import com.tss.service.CustomerService;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mainLoop:
        while (true) {
            System.out.println("\nWelcome to the Food Management System");
            System.out.println("Please choose a role:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int role;
            try {
                role = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (role) {
                case 1 -> {
                    AdminService.handleAdmin(scanner);
                }
                case 2 -> {
                    if (AuthService.authenticate(scanner)) {
                        User user = AuthService.getLoggedInUser();
                        CustomerService.handleCustomer(scanner, user); 
                    } else {
                        System.out.println("Authentication failed.");
                    }
                }
                case 3 -> {
                    System.out.println("Thank you! Goodbye.");
                    break mainLoop;
                }
                default -> System.out.println("Invalid role selected.");
            }
        }

        scanner.close();
    }
}
