package com.tss.service;

import java.util.Scanner;
import com.tss.model.User;

public class AuthService {
    private static final UserService userService = new UserService();
    private static User currentUser;

    public static boolean authenticate(Scanner scanner) {
        System.out.println("1. Signup");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            boolean success = userService.signUp(new User(username, password));
            if (success) {
                currentUser = new User(username, password);
                System.out.println("✅ Signup successful!");
            } else {
                System.out.println("❌ Username already exists.");
            }
            return success;

        } else if (choice == 2) {
            int attempts = 3;
            while (attempts-- > 0) {
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (userService.login(username, password)) {
                    currentUser = new User(username, password);
                    System.out.println("✅ Login successful!");
                    return true;
                } else {
                    if (attempts > 0)
                        System.out.println("❌ Incorrect password. Attempts left: " + attempts);
                    else
                        System.out.println("❌ Too many failed attempts. Access denied.");
                }
            }
            return false;

        } else {
            System.out.println("❌ Invalid choice.");
            return false;
        }
    }

    public static User getLoggedInUser() {
        return currentUser;
    }
}
