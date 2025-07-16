package com.tss.test;

import java.util.Scanner;

import com.tss.model.User;
import com.tss.service.AdminService;
import com.tss.service.AuthService;
import com.tss.service.CustomerService;

public class Test {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("🍽️ Welcome to the Food Management System");
		System.out.println("Choose role:");
		System.out.println("1. Admin");
		System.out.println("2. Customer");

		int role = scanner.nextInt();
		scanner.nextLine();

		switch (role) {
		case 1 -> AdminService.handleAdmin(scanner);
		case 2 -> {
			if (AuthService.authenticate(scanner)) {
				User user = AuthService.getLoggedInUser();
				CustomerService.handleCustomer(scanner, user);
			} else {
				System.out.println("❌ Authentication failed. Exiting...");
			}
		}
		default -> System.out.println("❌ Invalid role selected.");
		}

		scanner.close();
	}
}
