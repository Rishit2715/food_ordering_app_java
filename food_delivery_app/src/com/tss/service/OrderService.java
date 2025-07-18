package com.tss.service;

import com.tss.model.Order;
import com.tss.model.menu.MenuItem;

public class OrderService {
    private Order order = new Order();

    public void addItemToOrder(MenuItem item, int quantity) {
        order.addItem(item, quantity);
    }

    public Order getOrder() {
        return order;
    }


   
}
