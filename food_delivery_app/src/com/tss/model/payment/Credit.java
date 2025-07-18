package com.tss.model.payment;


import java.time.LocalDateTime;

public class Credit implements IPayment {
    private String userName;
    private String cardNumber;
    private String bankName;
    private int amount;
    private int pin;

    public Credit(String userName, String cardNumber, String bankName, int amount, int pin) {
        this.userName = userName.trim();
        this.cardNumber = cardNumber.trim();
        this.bankName = bankName.trim();
        this.amount = amount;
        this.pin = pin;
    }

    public boolean validate() {
        boolean isValid = true;

        if (userName.isEmpty()) {
            System.out.println("Invalid name.");
            isValid = false;
        }

        if (cardNumber.length() != 16) {
            System.out.println("Invalid card number. It must be exactly 16 digits.");
            isValid = false;
        }

        if (bankName.isEmpty()) {
            System.out.println("Bank name cannot be empty.");
            isValid = false;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0.");
            isValid = false;
        }

        if (pin < 1000 || pin > 9999) {
            System.out.println("Invalid PIN. It must be exactly 4 digits.");
            isValid = false;
        }

        return isValid;
    }

    public void payment() {
        LocalDateTime time = LocalDateTime.now();

        System.out.println("Credit Details");
        System.out.println("Name: " + userName);
        System.out.println("Timestamp: " + time);
        System.out.println("Payment successful through Credit Card.");
    }
}

