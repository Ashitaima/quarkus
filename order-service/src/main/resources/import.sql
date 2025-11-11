-- Початкові дані для замовлень
INSERT INTO orders (id, customerId, deliveryAddress, status) VALUES (1, 'customer-A', 'вул. Хрещатик, 1', 'PENDING');
INSERT INTO orders (id, customerId, deliveryAddress, status) VALUES (2, 'customer-B', 'пл. Ринок, 10', 'PENDING');
INSERT INTO orders (id, customerId, deliveryAddress, status) VALUES (3, 'customer-A', 'вул. Шевченка, 5', 'IN_DELIVERY');

-- Встановлюємо послідовність для автоінкременту
ALTER SEQUENCE orders_seq RESTART WITH 4;

