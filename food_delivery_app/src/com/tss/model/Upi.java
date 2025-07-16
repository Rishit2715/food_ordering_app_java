package com.tss.model;

public class Upi implements IPayment {
	private int amount;
	private String upiId;
	private int pin;

	public Upi(int amount, String upiId, int pin) {
		this.amount = amount;
		this.upiId = upiId;
		this.pin = pin;
	}

	public boolean validate() {
		boolean isValid = true;

		if (amount <= 0) {
			System.out.println("Amount must be greater than 0.");
			isValid = false;
		}

		if (!upiId.matches("^[a-zA-Z0-9._-]+@[a-zA-Z]+$")) {
			System.out.println("Invalid UPI ID format.");
			isValid = false;
		}

		if (pin < 1000 || pin > 9999) {
			System.out.println("Invalid PIN. It must be exactly 4 digits.");
			isValid = false;
		}

		return isValid;
	}

	public void payment() {
		System.out.println("UPI Details");
		System.out.println("UPI ID: " + upiId);
		System.out.println("Amount: " + amount);
		System.out.println("PIN Verified Successfully");
		System.out.println("Payment successful via UPI.");
	}
}
