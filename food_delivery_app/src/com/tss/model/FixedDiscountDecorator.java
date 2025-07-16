package com.tss.model;

public class FixedDiscountDecorator extends DiscountDecorator {
    private double threshold = 500;
    private double fixedDiscount = 50;

    public FixedDiscountDecorator(IDiscount discount) {
        super(discount);
    }

    @Override
    public double getDiscount(double totalAmount) {
        double base = super.getDiscount(totalAmount);
        return base + (totalAmount > threshold ? fixedDiscount : 0);
    }

    public double getFixedDiscount() {
        return fixedDiscount;
    }

    public double getThreshold() {
        return threshold;
    }
    
    
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setFixedDiscount(double fixedDiscount) {
        this.fixedDiscount = fixedDiscount;
    }
}
