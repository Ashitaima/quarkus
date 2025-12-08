package com.dronedelivery.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import java.time.LocalDateTime;

/**
 * Service responsible for consuming order events from Kafka
 * and creating notifications
 */
@ApplicationScoped
public class OrderEventConsumer {

    private static final Logger LOG = Logger.getLogger(OrderEventConsumer.class);
    private final ObjectMapper objectMapper;

    public OrderEventConsumer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Consume order events from Kafka and create notifications
     */
    @Incoming("order-events")
    public void consumeOrderEvent(String eventJson) {
        try {
            // Deserialize JSON string to OrderEventDTO
            OrderEventDTO event = objectMapper.readValue(eventJson, OrderEventDTO.class);

            LOG.infof("Received order event: %s", event);

            // Create notification based on event type
            Notification notification = new Notification();
            notification.type = determineNotificationType(event.eventType);
            notification.message = buildNotificationMessage(event);
            notification.timestamp = LocalDateTime.now();

            // Persist notification to MongoDB
            notification.persist();

            LOG.infof("Created notification for order event - Order ID: %d, Type: %s",
                     event.orderId, event.eventType);
        } catch (Exception e) {
            LOG.errorf(e, "Failed to process order event: %s", eventJson);
        }
    }

    /**
     * Determine notification type based on event type
     */
    private String determineNotificationType(String eventType) {
        return switch (eventType) {
            case "CREATED" -> "ORDER_CREATED";
            case "UPDATED" -> "ORDER_UPDATED";
            case "STATUS_CHANGED" -> "ORDER_STATUS_CHANGED";
            case "DELETED" -> "ORDER_DELETED";
            default -> "ORDER_EVENT";
        };
    }

    /**
     * Build human-readable notification message
     */
    private String buildNotificationMessage(OrderEventDTO event) {
        return switch (event.eventType) {
            case "CREATED" -> String.format(
                "Нове замовлення #%d створено для %s. Доставка до: %s",
                event.orderId, event.customerName, event.destination
            );
            case "UPDATED" -> String.format(
                "Замовлення #%d оновлено для %s",
                event.orderId, event.customerName
            );
            case "STATUS_CHANGED" -> String.format(
                "Статус замовлення #%d змінено на: %s",
                event.orderId, event.status
            );
            case "DELETED" -> String.format(
                "Замовлення #%d видалено",
                event.orderId
            );
            default -> String.format(
                "Подія замовлення #%d: %s",
                event.orderId, event.eventType
            );
        };
    }

    /**
     * DTO for receiving order events from Kafka
     */
    public static class OrderEventDTO {
        public Long orderId;
        public String customerName;
        public String destination;
        public Double weight;
        public String status;
        public LocalDateTime timestamp;
        public String eventType;

        @Override
        public String toString() {
            return "OrderEventDTO{" +
                    "orderId=" + orderId +
                    ", customerName='" + customerName + '\'' +
                    ", eventType='" + eventType + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}

