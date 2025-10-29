package com.dronedelivery.orderservice; // Ваш пакет

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap; // Краще для доступу з різних потоків

@ApplicationScoped // Робить цей клас доступним для ін'єкції
public class OrderRepository {

    // Використаємо ConcurrentHashMap для "бази даних" в пам'яті
    private Map<String, Order> orders = new ConcurrentHashMap<>();

    public OrderRepository() {
        // Додамо фейкові дані при старті
        Order order1 = new Order("order-1", "customer-A", "вул. Хрещатик, 1");
        Order order2 = new Order("order-2", "customer-B", "пл. Ринок, 10");
        orders.put(order1.id, order1);
        orders.put(order2.id, order2);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public void add(Order order) {
        orders.put(order.id, order);
    }

    // Метод для оновлення статусу замовлення
    public Optional<Order> updateStatus(String id, OrderStatus newStatus) {
        Optional<Order> orderOpt = findById(id);
        orderOpt.ifPresent(order -> order.status = newStatus);
        return orderOpt;
    }
}