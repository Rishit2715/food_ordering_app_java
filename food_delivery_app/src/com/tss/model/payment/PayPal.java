package com.tss.model.payment;


public class PayPal implements IPayment {
    private int amount;
    private String email;
    private int pin;

    public PayPal(int amount, String email, int pin) {
        this.amount = amount;
        this.email = email.trim();
        this.pin = pin;
    }

    public boolean validate() {
        boolean isValid = true;

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0.");
            isValid = false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Invalid email format.");
            isValid = false;
        }

        if (pin < 1000 || pin > 9999) {
            System.out.println("Invalid PIN. It must be exactly 4 digits.");
            isValid = false;
        }

        return isValid;
    }

    public void payment() {
        System.out.println("PayPal Details");
        System.out.println("Email: " + email);
        System.out.println("Amount: " + amount);
        System.out.println("PIN Verified Successfully");
        System.out.println("Payment successful via PayPal.");
    }
}
