package com.tss.service;

import java.util.Scanner;

import com.tss.model.Credit;
import com.tss.model.Debit;
import com.tss.model.IPayment;
import com.tss.model.PayPal;
import com.tss.model.Upi;

public class PaymentService {
	public IPayment collectPayment(Scanner scanner, int amount) {
		System.out.println("Choose Payment Method:");
		System.out.println("1. Credit Card");
		System.out.println("2. Debit Card");
		System.out.println("3. UPI");
		System.out.println("4. PayPal");

		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1 -> {
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("Card Number (16-digit): ");
			String card = scanner.nextLine();
			System.out.print("Bank Name: ");
			String bank = scanner.nextLine();
			int pin = inputPin(scanner);
			return new Credit(name, card, bank, amount, pin);
		}
		case 2 -> {
			System.out.print("Name: ");
			String name = scanner.nextLine();
			System.out.print("Card Number (16-digit): ");
			String card = scanner.nextLine();
			System.out.print("Bank Name: ");
			String bank = scanner.nextLine();
			int pin = inputPin(scanner);
			return new Debit(name, card, bank, amount, pin);
		}
		case 3 -> {
			System.out.print("UPI ID: ");
			String upiId = scanner.nextLine();
			int pin = inputPin(scanner);
			return new Upi(amount, upiId, pin);
		}
		case 4 -> {
			System.out.print("Email: ");
			String email = scanner.nextLine();
			int pin = inputPin(scanner);
			return new PayPal(amount, email, pin);
		}
		default -> {
			System.out.println("❌ Invalid choice. Defaulting to Cash.");
			return null;
		}
		}
	}

	private int inputPin(Scanner scanner) {
		System.out.print("Enter 4-digit PIN: ");
		return scanner.nextInt();
	}
}
