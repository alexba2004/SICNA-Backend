-- Insertar datos en la tabla student_users
INSERT INTO student_users (student_id, name, phone_number, email, password)
VALUES 
    ('200000', 'Jose Alejandro Briones Arroyo', '+1122334455', 'alejandro@example.com', 'estudiante123'),
    ('200003', 'Luis Octavio Lopez Martinez', '+9988776655', 'luis@example.com', 'estudiante012'),
    ('200001', 'Carlos Sánchez', '+9988776655', 'carlos@example.com', 'estudiante456'),
    ('200002', 'Elena Gómez', '+1122334455', 'elena@example.com', 'estudiante789');

-- Insertar datos en la tabla admin_users
INSERT INTO admin_users (employee_number, name, phone_number, email, password)
VALUES 
    ('2000002', 'Joan Salas Vera', '+1122334455', 'joan@example.com', 'contraseña789'),
    ('2000000', 'Juan Pérez', '+1234567890', 'juan@example.com', 'contraseña123'),
    ('2000001', 'María García', '+1987654321', 'maria@example.com', 'contraseña456'),
    ('2000003', 'Ana Martínez', '+9988776655', 'ana@example.com', 'contraseña012');

-- Insertar datos en la tabla areas
INSERT INTO areas (name, description)
VALUES 
    ('Laboratorio de Cómputo 1', 'Laboratorio de cómputo equipado con computadoras y software especializado'),
    ('Laboratorio de Cómputo 2', 'Laboratorio de cómputo equipado con computadoras y software especializado'),
    ('Laboratorio de Cómputo 3', 'Laboratorio de cómputo equipado con computadoras y software especializado'),
    ('Laboratorio de Cómputo 4', 'Laboratorio de cómputo equipado con computadoras y software especializado');

-- Insertar datos en la tabla inventory_items
INSERT INTO inventory_items (name, brand, category, price, description, image_file_name, created_at, area_id)
VALUES 
    ('Monitor', 'Samsung', 'Electrónica', 300.00, 'Monitor de 24 pulgadas', 'monitor.jpg', '2023-01-10', 1),
    ('Teclado', 'Logitech', 'Periférico', 50.00, 'Teclado inalámbrico', 'keyboard.jpg', '2023-02-15', 2),
    ('Ratón', 'Razer', 'Periférico', 30.00, 'Ratón óptico', 'mouse.jpg', '2023-03-20', 3),
    ('CPU', 'Xtrem', 'Equipo de Cómputo', 800.00, 'Computadora de escritorio', 'cpu.jpg', '2023-04-25', 4);

-- Insertar datos en la tabla reports
INSERT INTO reports (creation_date, description, status, student_id, admin_user_id)
VALUES 
    ('2023-01-15', 'Monitor roto', 'ADEUDO', '200000', '2000002'),
    ('2023-02-20', 'Teclado sin 4 teclas', 'PAGADO', '200003', '2000000'),
    ('2023-03-25', 'Ratón desgastado', 'ADEUDO', '200001', '2000001'),
    ('2023-04-30', 'CPU con problemas de arranque', 'PAGADO', '200002', '2000003');
