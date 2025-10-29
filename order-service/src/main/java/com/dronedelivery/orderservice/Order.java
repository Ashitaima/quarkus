package com.dronedelivery.orderservice; // Ваш пакет

public class Order {
    public String id;
    public String customerId;
    public String deliveryAddress;
    public OrderStatus status;

    // Порожній конструктор (потрібен для JSON)
    public Order() {}

    // Конструктор для фейкових даних
    public Order(String id, String customerId, String deliveryAddress) {
        this.id = id;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.PENDING;
    }
}