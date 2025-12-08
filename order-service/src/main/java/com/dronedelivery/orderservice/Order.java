package com.dronedelivery.orderservice;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "destination")
    private String destination;

    @Column(name = "weight")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Порожній конструктор (потрібен для JPA)
    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    // Конструктор для створення нових замовлень
    public Order(String customerName, String destination, Double weight) {
        this.customerName = customerName;
        this.destination = destination;
        this.weight = weight;
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}