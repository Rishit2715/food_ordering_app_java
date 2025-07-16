package com.tss.model;

public class BaseDiscount implements IDiscount {
	@Override
	public double getDiscount(double totalAmount) {
		return 0;
	}
}
