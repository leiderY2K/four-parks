-- Eliminar todos los datos de las tablas existentes para evitar duplicados
DELETE FROM `FOURPARKSDATABASE`.`USER`;
DELETE FROM `FOURPARKSDATABASE`.`DOCUMENTTYPE`;
DELETE FROM `FOURPARKSDATABASE`.`PARKING`;
DELETE FROM `FOURPARKSDATABASE`.`CITY`;
DELETE FROM `FOURPARKSDATABASE`.`ADDRESS`;
DELETE FROM `FOURPARKSDATABASE`.`PARKINGTYPE`;
DELETE FROM `FOURPARKSDATABASE`.`SCHEDULE`;

-- Insertar tipos de documento primero
INSERT INTO `FOURPARKSDATABASE`.`DOCUMENTTYPE` (`IDDOCTYPE`, `DESCDOCTYPE`)
VALUES
    ('CC', 'Cedula'),
    ('TI', 'Tarjeta de identidad'),
    ('CI', 'Cedula de extranjeria');

-- Insertar usuarios después de los tipos de documento
INSERT INTO `FOURPARKSDATABASE`.`USER` (`IDUSER`, `FIRSTNAME`, `LASTNAME`, `FK_IDDOCTYPE`, `EMAIL`, `PHONE`)
VALUES
    ('1234567890123', 'Juan', 'Perez', 'CC', 'juan@example.com', '123456789'),    -- Cedula
    ('9876543210987', 'Maria', 'Gomez', 'TI', 'maria@example.com', '987654321'),    -- Tarjeta de identidad
    ('4567890123456', 'Pedro', 'Martinez', 'CC', 'pedro@example.com', '456789012'), -- Cedula
    ('7890123456789', 'Ana', 'Lopez', 'CI', 'ana@example.com', '789012345'),      -- Cedula de extranjeria
    ('6543210987654', 'Luis', 'Rodriguez', 'TI', 'luis@example.com', '654321098'); -- Tarjeta de identidad

-- Insertar Autenticacion de usuarios después de los usuarios
INSERT INTO `FOURPARKSDATABASE`.`USER_AUTHENTICATION` (`IDUSER`, `FK_IDDOCTYPE`, `USERNAME`, `PASSWORD`, `ROLE`, `ATTEMPTS`,`ISBLOCKED`)
VALUES
    ('1234567890123', 'CC', 'juanperez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('9876543210987', 'TI', 'mariagomez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('4567890123456', 'CC', 'pedromartinez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('7890123456789', 'CI', 'analopez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('6543210987654', 'TI', 'luisrodriguez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0);

-- Insertar ciudades
INSERT INTO `FOURPARKSDATABASE`.`CITY` (`IDCITY`, `NAME`, `B_TOP`, `B_BOTTOM`, `B_LEFT`, `B_RIGHT`, `X_CENTER`, `Y_CENTER`)
VALUES
    ('CAL', 'Cali', 3.4961, 3.3611, -76.5716, -76.4647, 3.4301, -76.5129),
    ('BAQ', 'Barranquilla', 11.0405, 10.9231, -74.8785, -74.7571, 10.9725, -74.8018),
    ('BGT', 'Bogota', 4.7694, 4.4861, -74.2034, -74.0232, 4.6596, -74.0915),
    ('MED', 'Medellin', 6.3139, 6.1729, -75.6478, -75.5190, 6.2385, -75.5760),
    ('CTG', 'Cartagena', 10.4494, 10.3931, -75.4338, -75.5619, 10.3956, -75.5008),
    ('MQR', 'Mosquera', 4.7238, 4.6958, -74.2491, -74.2102, 4.7041, -74.2339),
    ('VIL', 'Villavicencio', 4.1722, 4.1054, -73.6642, -73.5853, 4.1374, -73.6247),
    ('BCM', 'Bucaramanga', 7.1521, 7.0861, -73.1455, -73.0790, 7.1244, -73.1183),
    ('PAS', 'Pasto', 1.2341, 1.1796, -77.3032, -77.2431, 1.2134, -77.2782),
    ('SOA', 'Soacha', 4.6116, 4.5564, -74.2496, -74.1722, 4.5817, -74.2197);

-- Insertar direcciones en la tabla ADDRESS
INSERT INTO `FOURPARKSDATABASE`.`ADDRESS` (`COORDINATESX`, `COORDINATESY`, `DESCADDRESS`)
VALUES
    (3.4433, -76.5248, 'Calle 16, Sucre, Comuna 9, Cali, Sur, Valle del Cauca'),
    (10.99364, -74.80474, 'Carrera 46, Barrio Colombia, Localidad Norte - Centro Historico, Barranquilla'),
    (6.2421, -75.5688, 'Calle 42, Colon, Comuna 10 - La Candelaria, Medellin, Valle de Aburra, Antioquia'),
    (10.4238, -75.5453, 'Avenida Daniel Lemaitre, Getsemani, Cartagena, Dique, Bolivar'),
    (4.7075, -74.2298, 'Calle 5, Centro Mosquera, Mosquera, Sabana Occidente, Cundinamarca'),
    (4.1350, -73.6219, 'Avenida 19, Bello Horizonte, Comuna 5, Villavicencio, Meta'),
    (7.1254, -73.1180, 'Carrera 27, Comuna 13 - Oriental, Bucaramanga, Metropolitana'),
    (1.2138, -77.2820, 'Carrera 25, Las Cuadras, Comuna 1, Pasto, Centro, Nariño'),
    (4.5804, -74.2183, 'Carrera 6, San Luis, Soacha Central, Soacha ciudad, Soacha, Cundinamarca'),
    (4.6514, -74.0676, 'Calle 63A, Localidad Barrios Unidos, Bogota'),
    (4.6478, -74.0622, 'Carrera 9A, Chapinero, Localidad Chapinero'),
    (4.6606, -74.0732, 'Carrera 28, Siete de Agosto, Localidad Barrios Unidos, Bogota'),
    (4.6645, -74.0914, 'Avenida Calle 63, Parque Simon Bolivar-CAN, Localidad Teusaquillo, Bogota'),
    (4.6780, -74.1179, 'Carrera 85D, Localidad Engativa, Bogota'),
    (4.6301, -74.1551, 'Calle 35 Sur, Ciudad Kennedy Norte, Localidad Kennedy, Bogota'),
    (4.5756, -74.1112, 'Carrera 13B, Gustavo Restrepo, Localidad Rafael Uribe Uribe, Bogota'),
    (4.5369, -74.0866, 'Transversal 13D Este, Los Pinares, Localidad San Cristobal, Bogota'),
    (4.5031, -74.1068, 'Carrera 4, Chico Sur, Localidad Usme, Bogota'),
    (4.6172, -74.1937, 'Calle 69B Sur, La Esmeralda, Localidad Bosa, Bogota');

-- Insertar tipos de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGTYPE` (`IDPARKINGTYPE`, `DESCPARKINGTYPE`)
VALUES
    ('UNC', 'Sin cubrir'),
    ('SEC', 'Semi cubierto'),
    ('COV', 'Cubierto');

-- Insertar horarios
INSERT INTO `FOURPARKSDATABASE`.`SCHEDULE` (`IDSCHEDULE`, `SCHEDULETYPE`)
VALUES
    (1, 'Dias de semana'),
    (2, 'Fines de semana'),
    (3, 'Todos los dias');

-- Insertar estacionamientos
INSERT INTO `FOURPARKSDATABASE`.`PARKING` (`NAMEPARK`, `CAPACITY`, `OCUPABILITY`, `PHONE`, `EMAIL`, `FK_COORDINATESX`, `FK_COORDINATESY`, `FK_IDCITY`, `FK_IDSCHEDULE`, `FK_ADMIN_IDUSER`, `FK_ADMIN_IDDOCTYPE`, `FK_IDPARKINGTYPE`, `STARTTIME`, `ENDTIME`)
VALUES
    ('Parqueadero 1', 0, 1, '1234567890', 'parqueadero1@example.com', 3.4433, -76.5248, 'CAL', 1, '9876543210987', 'TI', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 2', 0, 1, '1234567890', 'parqueadero2@example.com', 10.9936, -74.8047, 'BAQ', 2, '4567890123456', 'CC', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 3', 0, 1, '1234567890', 'parqueadero3@example.com', 6.2421, -75.5688, 'MED', 2, '9876543210987', 'TI', 'COV', '08:00:00', '18:00:00'),
    ('Parqueadero 4', 0, 1, '1234567890', 'parqueadero4@example.com', 10.4238, -75.5453, 'CTG', 3, '4567890123456', 'CC', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 5', 6, 1,'1234567890', 'parqueadero_a@example.com', 4.7075, -74.2298, 'MQR', 3, '9876543210987', 'TI', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 6', 7, 1, '1234567890', 'parqueadero_b@example.com', 4.1350, -73.6219, 'VIL', 1, '4567890123456', 'CC', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 7', 8, 1, '1234567890', 'parqueadero_c@example.com', 7.1254, -73.1180, 'BCM', 1, '9876543210987', 'TI', 'COV', '08:00:00', '18:00:00'),
    ('Parqueadero 8', 9, 1, '1234567890', 'parqueadero_d@example.com', 1.2138, -77.2820, 'PAS', 2, '4567890123456', 'CC', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 9', 10, 1, '1234567890', 'parqueadero_e@example.com', 4.5804, -74.2183, 'SOA', 3, '9876543210987', 'TI', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 10', 10, 1, '1234567890', 'parqueadero10@example.com', 4.6514, -74.0676, 'BGT', 3, '9876543210987', 'TI', 'COV', '08:00:00', '18:00:00'),
    ('Parqueadero 11', 10, 1, '1234567890', 'parqueadero11@example.com', 4.6478, -74.0622, 'BGT', 2, '4567890123456', 'CC', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 12', 10, 1, '1234567890', 'parqueadero12@example.com', 4.6606, -74.0732, 'BGT', 2, '9876543210987', 'TI', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 13', 10, 1, '1234567890', 'parqueadero13@example.com', 4.6645, -74.0914, 'BGT', 1, '4567890123456', 'CC', 'COV', '08:00:00', '18:00:00'),
    ('Parqueadero 14', 10, 1, '1234567890', 'parqueadero14@example.com', 4.6780, -74.1179, 'BGT', 1, '9876543210987', 'TI', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 15', 10, 1, '1234567890', 'parqueadero15@example.com', 4.6301, -74.1551, 'BGT', 1, '4567890123456', 'CC', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 16', 10, 1, '1234567890', 'parqueadero16@example.com', 4.5756, -74.1112, 'BGT', 2, '9876543210987', 'TI', 'COV', '08:00:00', '18:00:00'),
    ('Parqueadero 17', 10, 1, '1234567890', 'parqueadero17@example.com', 4.5369, -74.0866, 'BGT', 2, '4567890123456', 'CC', 'UNC', '08:00:00', '18:00:00'),
    ('Parqueadero 18', 10, 1, '1234567890', 'parqueadero18@example.com', 4.5031, -74.1068, 'BGT', 2, '9876543210987', 'TI', 'SEC', '08:00:00', '18:00:00'),
    ('Parqueadero 19', 10, 1, '1234567890', 'parqueadero19@example.com', 4.6172, -74.1937, 'BGT', 1, '4567890123456', 'CC', 'COV', '08:00:00', '18:00:00');
    
-- Insertar Tipos de Vehiculos
INSERT INTO `FOURPARKSDATABASE`.`VEHICLETYPE` (`IDVEHICLETYPE`,`DESCVEHICLETYPE`)
VALUES
    ('CAR', 'AUTOMOVIL'),
    ('MOT', 'MOTOCICLETA'),
    ('BIC', 'BICICLETA');
    
-- Insertar espacios de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGSPACE` (`IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `ISUNCOVERED`) 
VALUES
    -- Parqueadero 1 
    (1, 1, 'CAL', 'CAR', 1),
    (2, 1, 'CAL', 'CAR', 1),   
    (3, 1, 'CAL', 'CAR', 1),
    (4, 1, 'CAL', 'MOT', 1),   
    -- Parqueadero 2
    (1, 2, 'BAQ', 'CAR', 0),
    (2, 2, 'BAQ', 'CAR', 0),   
    (3, 2, 'BAQ', 'CAR', 0),   
    (4, 2, 'BAQ', 'CAR', 1),
    (5, 2, 'BAQ', 'CAR', 1),
    (6, 2, 'BAQ', 'CAR', 1),
    (7, 2, 'BAQ', 'MOT', 0),   
    (8, 2, 'BAQ', 'MOT', 0),
    (9, 2, 'BAQ', 'MOT', 0),
    (10, 2, 'BAQ', 'MOT', 0),
    (11, 2, 'BAQ', 'MOT', 1),
    (12, 2, 'BAQ', 'MOT', 1),
    (13, 2, 'BAQ', 'MOT', 1),
    (14, 2, 'BAQ', 'MOT', 1),
    -- Parqueadero 3
    (1, 3, 'MED', 'CAR', 0),
    (2, 3, 'MED', 'CAR', 0),   
    (3, 3, 'MED', 'CAR', 0),   
    (4, 3, 'MED', 'CAR', 0),
    -- Parqueadero 4
    (1, 4, 'CTG', 'CAR', 1),
    (2, 4, 'CTG', 'CAR', 1),   
    (3, 4, 'CTG', 'CAR', 1),   
    (4, 4, 'CTG', 'CAR', 1),
    -- Parqueadero 13
    (1, 13, 'BGT', 'CAR', 0),
    (2, 13, 'BGT', 'CAR', 0),   
    (3, 13, 'BGT', 'CAR', 0),   
    (4, 13, 'BGT', 'CAR', 0),
    -- Parqueadero 14
    (1, 14, 'BGT', 'CAR', 1),
    (2, 14, 'BGT', 'CAR', 1),   
    (3, 14, 'BGT', 'CAR', 1),   
    (4, 14, 'BGT', 'CAR', 1),
    -- Parqueadero 15
    (1, 15, 'BGT', 'CAR', 1),
    (2, 15, 'BGT', 'CAR', 1),   
    (3, 15, 'BGT', 'CAR', 1),   
    (4, 15, 'BGT', 'CAR', 1),
    -- Parqueadero 16
    (1, 16, 'BGT', 'CAR', 0),
    (2, 16, 'BGT', 'CAR', 0),   
    (3, 16, 'BGT', 'CAR', 0),   
    (4, 16, 'BGT', 'CAR', 0);

-- Insertar estados de reserva
INSERT INTO `FOURPARKSDATABASE`.`RESERVATIONSTATUS` (`IDRESSTATUS`, `DESCRESSTATUS`) 
VALUES
    ('PEN', 'Pendiente'),
    ('CON', 'Confirmada'),
    ('CAN', 'Cancelada'),
    ('CUR', 'En curso'),
    ('COM', 'Completada'),
    ('NPR', 'No presentado');




    
