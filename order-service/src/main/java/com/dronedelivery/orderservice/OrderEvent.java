package com.dronedelivery.orderservice;

import java.time.LocalDateTime;

/**
 * Data class representing an order event for Kafka messaging
 */
public class OrderEvent {

    private Long orderId;
    private String customerName;
    private String destination;
    private Double weight;
    private OrderStatus status;
    private LocalDateTime timestamp;
    private String eventType; // CREATED, UPDATED, STATUS_CHANGED, DELETED

    // Default constructor for JSON deserialization
    public OrderEvent() {
    }

    // Constructor for creating events from Order entity
    public OrderEvent(Long orderId, String customerName, String destination,
                     Double weight, OrderStatus status, String eventType) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    // Static factory methods for different event types
    public static OrderEvent created(Order order) {
        return new OrderEvent(
            order.getId(),
            order.getCustomerName(),
            order.getDestination(),
            order.getWeight(),
            order.getStatus(),
            "CREATED"
        );
    }

    public static OrderEvent updated(Order order) {
        return new OrderEvent(
            order.getId(),
            order.getCustomerName(),
            order.getDestination(),
            order.getWeight(),
            order.getStatus(),
            "UPDATED"
        );
    }

    public static OrderEvent statusChanged(Order order) {
        return new OrderEvent(
            order.getId(),
            order.getCustomerName(),
            order.getDestination(),
            order.getWeight(),
            order.getStatus(),
            "STATUS_CHANGED"
        );
    }

    public static OrderEvent deleted(Long orderId) {
        OrderEvent event = new OrderEvent();
        event.setOrderId(orderId);
        event.setEventType("DELETED");
        event.setTimestamp(LocalDateTime.now());
        return event;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", destination='" + destination + '\'' +
                ", status=" + status +
                ", eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

