package com.dronedelivery.orderservice;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    /**
     * Знайти всі замовлення клієнта
     */
    public List<Order> findByCustomer(String customerName) {
        return list("customerName", customerName);
    }

    /**
     * Знайти замовлення за статусом
     */
    public List<Order> findByStatus(OrderStatus status) {
        return list("status", status);
    }

    /**
     * Знайти активні замовлення (не доставлені та не скасовані)
     */
    public List<Order> findActiveOrders() {
        return list("status != ?1 and status != ?2",
                    OrderStatus.DELIVERED, OrderStatus.CANCELED);
    }

    /**
     * Підрахувати кількість замовлень клієнта
     */
    public long countByCustomer(String customerName) {
        return count("customerName", customerName);
    }

    /**
     * Знайти замовлення по частині адреси доставки
     */
    public List<Order> findByDestinationContaining(String partialAddress) {
        return list("destination like ?1", "%" + partialAddress + "%");
    }
}

