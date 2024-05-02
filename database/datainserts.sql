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
    ('1234567890123', 'Juan', 'Pérez', 'CC', 'juan@example.com', '123456789'),    -- Cedula
    ('9876543210987', 'María', 'Gómez', 'TI', 'maria@example.com', '987654321'),    -- Tarjeta de identidad
    ('4567890123456', 'Pedro', 'Martínez', 'CC', 'pedro@example.com', '456789012'), -- Cedula
    ('7890123456789', 'Ana', 'López', 'CI', 'ana@example.com', '789012345'),      -- Cedula de extranjeria
    ('6543210987654', 'Luis', 'Rodríguez', 'TI', 'luis@example.com', '654321098'); -- Tarjeta de identidad

-- Insertar Autenticacion de usuarios después de los usuarios
INSERT INTO `FOURPARKSDATABASE`.`USER_AUTHENTICATION` (`IDUSER`, `FK_IDDOCTYPE`, `USERNAME`, `PASSWORD`, `ROLE`, `ATTEMPTS`,`ISBLOCKED`)
VALUES
    ('1234567890123', 'CC', 'juanperez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('9876543210987', 'TI', 'mariagomez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('4567890123456', 'CC', 'pedromartinez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('7890123456789', 'CI', 'analopez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0),
    ('6543210987654', 'TI', 'luisrodriguez', '$2a$10$QaazF..XTCslpdfjsNS.ZO3O6VhyG4roY6JJcE9TYc93W5ZbMbfwa','CLIENT',0,0);

-- Insertar ciudades
INSERT INTO `FOURPARKSDATABASE`.`CITY` (`IDCITY`, `NAME`, `B_TOP`, `B_BOTTOM`, `B_LEFT`, `B_RIGHT`)
VALUES
    ('CAL', 'Cali', 3.4961, 3.3611, -76.5716, -76.4647),
    ('BAQ', 'Barranquilla', 11.0405, 10.9231, -74.8785, -74.7571),
    ('BGT', 'Bogota', 4.7694, 4.4861, -74.2034, -74.0232),
    ('MED', 'Medellin', 6.3139, 6.1729, -75.6478, -75.5190),
    ('CTG', 'Cartagena', 10.4494, 10.3931, -75.4338, -75.5619),
    ('MQR', 'Mosquera', 4.7238, 4.6958, -74.2491, -74.2102),
    ('VIL', 'Villavicencio', 4.1722, 4.1054, -73.6642, -73.5853),
    ('BCM', 'Bucaramanga', 7.1521, 7.0861, -73.1455, -73.0790),
    ('PAS', 'Pasto', 1.2341, 1.1796, -77.3032, -77.2431),
    ('SOA', 'Soacha', 4.6116, 4.5564, -74.2496, -74.1722);

-- Insertar direcciones en la tabla ADDRESS
INSERT INTO `FOURPARKSDATABASE`.`ADDRESS` (`COORDINATESX`, `COORDINATESY`, `DESCADDRESS`)
VALUES
    (3.4433, -76.5248, 'Calle 16, Sucre, Comuna 9, Cali, Sur, Valle del Cauca'),
    (10.99364, -74.80474, 'Carrera 46, Barrio Colombia, Localidad Norte - Centro Histórico, Barranquilla'),
    (6.2421, -75.5688, 'Calle 42, Colón, Comuna 10 - La Candelaria, Medellín, Valle de Aburrá, Antioquia'),
    (10.4238, -75.5453, 'Avenida Daniel Lemaitre, Getsemaní, Cartagena, Dique, Bolívar'),
    (4.7075, -74.2298, 'Calle 5, Centro Mosquera, Mosquera, Sabana Occidente, Cundinamarca'),
    (4.1350, -73.6219, 'Avenida 19, Bello Horizonte, Comuna 5, Villavicencio, Meta'),
    (7.1254, -73.1180, 'Carrera 27, Comuna 13 - Oriental, Bucaramanga, Metropolitana'),
    (1.2138, -77.2820, 'Carrera 25, Las Cuadras, Comuna 1, Pasto, Centro, Nariño'),
    (4.5804, -74.2183, 'Carrera 6, San Luis, Soacha Central, Soacha ciudad, Soacha, Cundinamarca'),
    (4.6514, -74.0676, 'Calle 63A, Localidad Barrios Unidos, Bogota'),
    (4.6478, -74.0622, 'Carrera 9A, Chapinero, Localidad Chapinero'),
    (4.6606, -74.0732, 'Carrera 28, Siete de Agosto, Localidad Barrios Unidos, Bogota'),
    (4.6645, -74.0914, 'Avenida Calle 63, Parque Simón Bolivar-CAN, Localidad Teusaquillo, Bogota'),
    (4.6780, -74.1179, 'Carrera 85D, Localidad Engativá, Bogota'),
    (4.6301, -74.1551, 'Calle 35 Sur, Ciudad Kennedy Norte, Localidad Kennedy, Bogota'),
    (4.5756, -74.1112, 'Carrera 13B, Gustavo Restrepo, Localidad Rafael Uribe Uribe, Bogota'),
    (4.5369, -74.0866, 'Transversal 13D Este, Los Pinares, Localidad San Cristóbal, Bogota'),
    (4.5031, -74.1068, 'Carrera 4, Chicó Sur, Localidad Usme, Bogota'),
    (4.6172, -74.1937, 'Calle 69B Sur, La Esmeralda, Localidad Bosa, Bogota');

-- Insertar tipos de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGTYPE` (`IDPARKINGTYPE`, `DESCPARKINGTYPE`)
VALUES
    ('UNC', 'Sin cubrir'),
    ('SEC', 'Semi cubierto'),
    ('COV', 'Cubierto');

-- Insertar horarios
INSERT INTO `FOURPARKSDATABASE`.`SCHEDULE` (`IDSCHEDULE`, `STARTTIME`, `ENDTIME`, `SCHEDULETYPE`)
VALUES
    (1, '08:00:00', '18:00:00', 'Días de semana'),
    (2, '08:00:00', '20:00:00', 'Fines de semana'),
    (3, '00:00:00', '23:59:59', 'Tiempo completo');

-- Insertar estacionamientos
INSERT INTO `FOURPARKSDATABASE`.`PARKING` (`NAMEPARK`, `CAPACITY`, `DISPONIBILITY`, `PHONE`, `EMAIL`, `FK_COORDINATESX`, `FK_COORDINATESY`, `FK_IDCITY`, `FK_IDSCHEDULE`, `FK_ADMIN_IDUSER`, `FK_ADMIN_IDDOCTYPE`, `FK_IDPARKINGTYPE`)
VALUES
    ('Parqueadero 1', 50, 30, '1234567890', 'parqueadero1@example.com', 3.4433, -76.5248, 'CAL', 1, '9876543210987', 'TI', 'UNC'),
    ('Parqueadero 2', 100, 80, '1234567890', 'parqueadero2@example.com', 10.9936, -74.8047, 'BAQ', 2, '4567890123456', 'CC', 'SEC'),
    ('Parqueadero 3', 70, 50, '1234567890', 'parqueadero3@example.com', 6.2421, -75.5688, 'MED', 2, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 4', 80, 60, '1234567890', 'parqueadero4@example.com', 10.4238, -75.5453, 'CTG', 3, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 5', 6, 5, '1234567890', 'parqueadero_a@example.com', 4.7075, -74.2298, 'MQR', 3, '9876543210987', 'TI', 'UNC'),
    ('Parqueadero 6', 7, 6, '1234567890', 'parqueadero_b@example.com', 4.1350, -73.6219, 'VIL', 1, '4567890123456', 'CC', 'SEC'),
    ('Parqueadero 7', 8, 7, '1234567890', 'parqueadero_c@example.com', 7.1254, -73.1180, 'BCM', 1, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 8', 9, 8, '1234567890', 'parqueadero_d@example.com', 1.2138, -77.2820, 'PAS', 2, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 9', 10, 9, '1234567890', 'parqueadero_e@example.com', 4.5804, -74.2183, 'SOA', 3, '9876543210987', 'TI', 'SEC'),
    ('Parqueadero 10', 10, 8, '1234567890', 'parqueadero10@example.com', 4.6514, -74.0676, 'BGT', 3, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 11', 10, 8, '1234567890', 'parqueadero11@example.com', 4.6478, -74.0622, 'BGT', 2, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 12', 10, 8, '1234567890', 'parqueadero12@example.com', 4.6606, -74.0732, 'BGT', 2, '9876543210987', 'TI', 'SEC'),
    ('Parqueadero 13', 10, 8, '1234567890', 'parqueadero13@example.com', 4.6645, -74.0914, 'BGT', 1, '4567890123456', 'CC', 'COV'),
    ('Parqueadero 14', 10, 8, '1234567890', 'parqueadero14@example.com', 4.6780, -74.1179, 'BGT', 1, '9876543210987', 'TI', 'UNC'),
    ('Parqueadero 15', 10, 8, '1234567890', 'parqueadero15@example.com', 4.6301, -74.1551, 'BGT', 1, '4567890123456', 'CC', 'SEC'),
    ('Parqueadero 16', 10, 8, '1234567890', 'parqueadero16@example.com', 4.5756, -74.1112, 'BGT', 2, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 17', 10, 8, '1234567890', 'parqueadero17@example.com', 4.5369, -74.0866, 'BGT', 2, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 18', 10, 8, '1234567890', 'parqueadero18@example.com', 4.5031, -74.1068, 'BGT', 2, '9876543210987', 'TI', 'SEC'),
    ('Parqueadero 19', 10, 8, '1234567890', 'parqueadero19@example.com', 4.6172, -74.1937, 'BGT', 1, '4567890123456', 'CC', 'COV');
    
-- Insertar Tipos de Vehiculos
INSERT INTO `FOURPARKSDATABASE`.`VEHICLETYPE` (`IDVEHICLETYPE`,`DESCVEHICLETYPE`)
VALUES
    ('CAR', 'AUTOMOVIL'),
    ('MOT', 'MOTOCICLETA'),
    ('BIC', 'BICICLETA');


-- Insertar tarifas
INSERT INTO `FOURPARKSDATABASE`.`RATE` (`HOURCOST`, `RESERVATIONCOST`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `ISCOVERED`) 
VALUES 
    (5000, 10000, 1, 'CAL', 'CAR', 1),    
    (4000, 6000, 2, 'BAQ', 'MOT', 1),
    (7000, 12000, 3, 'MED', 'CAR', 0),
    (3000, 5000, 10, 'BGT', 'MOT', 0),
    (3000, 5000, 11, 'BGT', 'CAR', 1),
    (4000, 6000, 12, 'BGT', 'CAR', 0),
    (3000, 5000, 13, 'BGT', 'CAR', 1),
    (4000, 6000, 14, 'BGT', 'CAR', 0);
    
-- Insertar espacios de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGSPACE` (`IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `ISCOVERED`) 
VALUES
    -- Parqueadero 1 
    (1, 1, 'CAL', 'CAR', 1),
    (2, 1, 'CAL', 'CAR', 1),   
    (3, 1, 'CAL', 'MOT', 1),   
    (4, 1, 'CAL', 'CAR', 1),
    -- Parqueadero 2
    (1, 2, 'BAQ', 'CAR', 0),
    (2, 2, 'BAQ', 'CAR', 0),   
    (3, 2, 'BAQ', 'MOT', 0),   
    (4, 2, 'BAQ', 'MOT', 0),
    -- Parqueadero 3
    (1, 3, 'MED', 'CAR', 1),
    (2, 3, 'MED', 'CAR', 1),   
    (3, 3, 'MED', 'CAR', 1),   
    (4, 3, 'MED', 'CAR', 1),
    -- Parqueadero 4
    (1, 4, 'CTG', 'CAR', 1),
    (2, 4, 'CTG', 'CAR', 1),   
    (3, 4, 'CTG', 'CAR', 0),   
    (4, 4, 'CTG', 'CAR', 0);

-- Insertar Reservaciones de prueba
INSERT INTO `FOURPARKSDATABASE`.`RESERVATION` (`DATERES`, `STARTTIMERES`, `ENDTIMERES`, `CREATIONDATERES`, `TOTALRES`, `LICENSEPLATE`, `FK_IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `FK_CLIENT_IDUSER`, `FK_CLIENT_IDDOCTYPE`)
VALUES
    ('2024-05-01', '10:00:00', '12:00:00', '2024-05-01', NULL, 'ABC123', 1, 1, 'CAL', 'CAR', '1234567890123', 'CC'),
    ('2024-05-02', '14:00:00', '16:00:00', '2024-05-01', NULL, 'DEF456', 2, 1, 'CAL', 'CAR', '6543210987654', 'TI'),
    ('2024-05-03', '08:00:00', '10:00:00', '2024-05-01', NULL, 'GHI789', 3, 2, 'BAQ', 'MOT', '1234567890123', 'CC'),
    ('2024-05-04', '09:00:00', '11:00:00', '2024-05-01', NULL, 'JKL012', 4, 3, 'MED', 'CAR', '6543210987654', 'TI'),
    ('2024-05-05', '13:00:00', '15:00:00', '2024-05-01', NULL, 'MNO345', 2, 4, 'CTG', 'CAR', '1234567890123', 'CC');