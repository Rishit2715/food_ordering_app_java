package com.tss.model;

import java.time.LocalDate;

public class OccasionalDiscountDecorator implements IDiscount {
    private IDiscount base;
    private double extraDiscount;
    private LocalDate occasionDate;

    public OccasionalDiscountDecorator(IDiscount base, double extraDiscount, LocalDate occasionDate) {
        this.base = base;
        this.extraDiscount = extraDiscount;
        this.occasionDate = occasionDate;
    }

    
    public double getExtraDiscount() {
        return extraDiscount;
    }
    public LocalDate getOccasionDate() {
        return occasionDate;
    }
    public IDiscount getBase() {
        return base;
    }
    @Override
    public double getDiscount(double total) {
        double baseDiscount = base.getDiscount(total);
        LocalDate today = LocalDate.now();

        if (today.equals(occasionDate)) {
            return baseDiscount + extraDiscount;
        }
        return baseDiscount;
    }

    // Setters
    public void setExtraDiscount(double extraDiscount) {
        this.extraDiscount = extraDiscount;
    }

    public void setOccasionDate(LocalDate occasionDate) {
        this.occasionDate = occasionDate;
    }
}
