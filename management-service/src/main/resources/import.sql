-- Початкові дані для дронів
INSERT INTO drones (id, status, batteryLevel) VALUES (1, 'FREE', 100);
INSERT INTO drones (id, status, batteryLevel) VALUES (2, 'FREE', 85);
INSERT INTO drones (id, status, batteryLevel) VALUES (3, 'CHARGING', 20);
INSERT INTO drones (id, status, batteryLevel) VALUES (4, 'FREE', 95);

-- Встановлюємо послідовність для автоінкременту
ALTER SEQUENCE drones_seq RESTART WITH 5;

