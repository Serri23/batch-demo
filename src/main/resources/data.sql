CREATE TABLE customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    registration_date DATE NOT NULL
);

INSERT INTO customers (name, email, registration_date) VALUES
('Juan Perez', 'juan.perez@gmail.com', '2023-01-15'),
('Maria Garcia', 'maria.garcia@gmail.com', '2023-02-20'),
('Carlos Lopez', 'carlos.lopez@gmail.com', '2023-03-10'),
('Ana Torres', 'ana.torres@gmail.com', '2023-04-05'),
('Lucia Fernandez', 'lucia.fernandez@gmail.com', '2023-05-12'),
('Sofia Romero', 'sofia.romero@gmail.com', '2023-07-22'),
('Miguel Ruiz', 'miguel.ruiz@gmail.com', '2023-08-30'),
('Laura Gomez', 'laura.gomez@gmail.com', '2023-09-14'),
('David Herrera', 'david.herrera@gmail.com', '2023-10-01');

