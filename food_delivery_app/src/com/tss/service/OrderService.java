package com.tss.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.tss.model.MenuItem;
import com.tss.model.Order;

public class OrderService {
    private Order order = new Order();

    public void addItemToOrder(MenuItem item, int quantity) {
        order.addItem(item, quantity);
    }

    public Order getOrder() {
        return order;
    }


   
}
