package com.dronedelivery.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

/**
 * Service responsible for publishing order events to Kafka
 */
@ApplicationScoped
public class OrderEventProducer {

    private static final Logger LOG = Logger.getLogger(OrderEventProducer.class);
    private final ObjectMapper objectMapper;

    @Channel("order-events")
    Emitter<String> orderEventEmitter;

    public OrderEventProducer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Publish an order event to Kafka
     */
    public void publishOrderEvent(OrderEvent event) {
        try {
            // Serialize event to JSON string
            String eventJson = objectMapper.writeValueAsString(event);

            LOG.infof("Publishing order event: %s", event);
            orderEventEmitter.send(eventJson);
            LOG.infof("Successfully published order event for order ID: %d, type: %s",
                     event.getOrderId(), event.getEventType());
        } catch (Exception e) {
            LOG.errorf(e, "Failed to publish order event: %s", event);
        }
    }

    /**
     * Publish order created event
     */
    public void publishOrderCreated(Order order) {
        publishOrderEvent(OrderEvent.created(order));
    }

    /**
     * Publish order updated event
     */
    public void publishOrderUpdated(Order order) {
        publishOrderEvent(OrderEvent.updated(order));
    }

    /**
     * Publish order status changed event
     */
    public void publishOrderStatusChanged(Order order) {
        publishOrderEvent(OrderEvent.statusChanged(order));
    }

    /**
     * Publish order deleted event
     */
    public void publishOrderDeleted(Long orderId) {
        publishOrderEvent(OrderEvent.deleted(orderId));
    }
}

