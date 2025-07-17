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
        System.out.println("0. Cancel");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. UPI");
        System.out.println("4. PayPal");
        System.out.print("Enter choice: ");

        String choiceInput = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(choiceInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return null;
        }

        switch (choice) {
        case 0:
            return null; 

        case 1: {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Card Number (16-digit): ");
            String card = scanner.nextLine().replaceAll("\\s+", "");
            if (!card.matches("\\d{16}")) {
                System.out.println("Invalid card number. Must be 16 digits.");
                return null;
            }

            System.out.print("Bank Name: ");
            String bank = scanner.nextLine().trim();

            Integer pin = inputPin(scanner);
            if (pin == null) {
                return null;
            }

            return new Credit(name, card, bank, amount, pin.intValue());
        }

        case 2: {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Card Number (16-digit): ");
            String card = scanner.nextLine().replaceAll("\\s+", "");
            if (!card.matches("\\d{16}")) {
                System.out.println("Invalid card number. Must be 16 digits.");
                return null;
            }

            System.out.print("Bank Name: ");
            String bank = scanner.nextLine().trim();

            Integer pin = inputPin(scanner);
            if (pin == null) {
                return null;
            }

            return new Debit(name, card, bank, amount, pin.intValue());
        }

        case 3: {
            System.out.print("UPI ID: ");
            String upiId = scanner.nextLine().trim();
            if (!upiId.matches("^[\\w.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("Invalid UPI ID format.");
                return null;
            }

            Integer pin = inputPin(scanner);
            if (pin == null) {
                return null;
            }

            return new Upi(amount, upiId, pin.intValue());
        }

        case 4: {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                System.out.println("Invalid email.");
                return null;
            }

            Integer pin = inputPin(scanner);
            if (pin == null) {
                return null;
            }

            return new PayPal(amount, email, pin.intValue());
        }

        default:
            System.out.println("Invalid choice.");
            return null;
        }
    }

    
    private Integer inputPin(Scanner scanner) {
        System.out.print("Enter 4-digit PIN: ");
        String pinStr = scanner.nextLine().trim();
        if (pinStr.isEmpty()) {
            System.out.println("Cancelled.");
            return null;
        }
        if (!pinStr.matches("\\d{4}")) {
            System.out.println("Invalid PIN. It must be exactly 4 digits.");
            return null;
        }
        return Integer.valueOf(pinStr);
    }
}
