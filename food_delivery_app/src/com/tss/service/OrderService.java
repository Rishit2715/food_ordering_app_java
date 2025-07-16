package com.tss.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.tss.model.MenuItem;
import com.tss.model.Order;

public class OrderService {
    private Order order = new Order();
    private static final List<String> deliveryPartners = Arrays.asList("Swiggy", "Zomato");

    public void addItemToOrder(MenuItem item, int quantity) {
        order.addItem(item, quantity);
    }

    public Order getOrder() {
        return order;
    }

    public String getRandomDeliveryPartner() {
        return deliveryPartners.get(new Random().nextInt(deliveryPartners.size()));
    }
}
