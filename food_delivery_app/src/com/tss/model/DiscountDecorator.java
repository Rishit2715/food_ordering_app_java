package com.tss.model;

public abstract class DiscountDecorator implements IDiscount {
	protected IDiscount wrappedDiscount;

	public DiscountDecorator(IDiscount discount) {
		this.wrappedDiscount = discount;
	}

	@Override
	public double getDiscount(double totalAmount) {
		return wrappedDiscount.getDiscount(totalAmount);
	}
}
