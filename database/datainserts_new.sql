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
    ('SOA', 'Soacha', 4.6116, 4.5564, -74.2496, -74.1722, 4.5817, -74.2197),
    ('MTR', 'Monteria', 8.7842, 8.7129, -75.8341, -75.9153, 8.7507, -75.8784),
    ('MZS', 'Manizales', 5.0820, 5.0505, -75.4832, -75.5320, 5.0656, -75.5068),
    ('PER', 'Pereira', 4.7955, 5.0505, -75.6667, -75.7332, 4.8147, -75.6989);

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
    (4.6172, -74.1937, 'Calle 69B Sur, La Esmeralda, Localidad Bosa, Bogota'),
    (8.7521, -75.8820, '27-62, Carrera 10, Comuna 5, Montería'),
	(8.7533, -75.8838, 'zona 8, Carrera 8, Comuna 5, Montería'),
	(8.7545, -75.8834, 'Calle 29, Comuna 5, Montería'),
	(8.76055, -75.86652, 'Calle 50A, Urb. Villa Campestre, Comuna 8, Montería'),
	(8.73601, -75.86277, 'Carrera 37, Comuna 6, Montería'),
    (5.0689, -75.5160, 'Carrera 26, Versalles, Comuna La Estación, Manizales'),
	(5.0702, -75.4962, 'Carrera 16A, El Sol, Comuna La Estación, Manizales'),
	(5.0673, -75.5010, 'Carrera 20, San Jorge, Comuna La Estación, Manizales'),
	(5.0670, -75.5053, 'Avenida del Río, Santa Helena, Comuna La Estación, Manizales'),
	(5.0664, -75.5141, 'Carrera 24 del Ruiz, Centro, Comuna Cumanday, Manizales'),
    (4.8147, -75.6970, 'Carrera 7, Centro, Sector Lago Uribe, Centro, AMCO, Area Metropolitana Centro Occidente, Pereira'),
	(4.8131, -75.6955, '20-26, Carrera 9, Centro, Perimetro Urbano Pereira'),
	(4.8107, -75.6977, 'Carrera 12, Centro, Sector Lago Uribe, Centro, AMCO, Area Metropolitana Centro Occidente, Pereira'),
	(4.8083, -75.6792, 'La Rebeca Mall Plaza, #1-14, Carrera 13, Popular Modelo, Universidad, Perimetro Urbano Pereira'),
	(4.8034, -75.6862, 'Coco Parking, Carrera 18, Pinares de San Martin, Area Metropolitana Centro Occidente, Pereira'),
    (3.4310, -76.5274, 'Carrera 29A 1, Colseguros Andes, Comuna 10, Cali'),
	(3.4338, -76.5444, 'Carulla, Carrera 34, Viejo San Fernando, Comuna 19, Cali'),
	(3.4349, -76.5445, 'Carrera 34, Viejo San Fernando, Comuna 19, Cali'),
	(3.4444, -76.5460, 'Carrera 14, Acueducto San Antonio, Comuna 3, Cali'),
	(10.9542, -74.8076, 'Parqueo Sao Macarena, Avenida Cordialidad - Calle 56, La Sierra, Barranquilla'),
	(10.9268, -74.8020, 'Metropolitano, Vía Estadio, Ciudadela 20 de Julio, Barranquilla'),
	(10.9236, -74.8016, 'Parqueadero Velodromo, Avenida Circunvalar, Ciudadela 20 de Julio, Barranquilla'),
	(10.9222, -74.8062, 'Calle 47C, Ciudadela 20 de Julio, Barranquilla'),
	(6.2462, -75.5945, 'Calle 39D, Bolivariana, Comuna 11 - Laureles-Estadio, Medellín'),
	(6.2586, -75.6090, 'Parqueadero Santa Lucia, Calle 48C, La Pradera, Comuna 13 - San Javier, Medellín'),
	(6.2620, -75.6071, 'Parqueadero de Santa Rosa de Lima, Carrera 93, Metropolitano, Comuna 13 - San Javier, Medellín'),
	(6.2770, -75.5976, 'Calle 65, Robledo, Comuna 7 - Robledo, Medellín');
    


-- Insertar tipos de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGTYPE` (`IDPARKINGTYPE`, `DESCPARKINGTYPE`)
VALUES
    ('UNC', 'Sin cubrir'),
    ('SEC', 'Semi cubierto'),
    ('COV', 'Cubierto');

-- Insertar horarios
INSERT INTO `FOURPARKSDATABASE`.`SCHEDULE` (`IDSCHEDULE`, `STARTTIME`, `ENDTIME`, `SCHEDULETYPE`)
VALUES
    (1, '08:00:00', '18:00:00', 'Dias de semana'),
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
    ('Parqueadero 10', 20, 8, '1234567890', 'parqueadero10@example.com', 4.6514, -74.0676, 'BGT', 3, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 11', 15, 9, '1234567890', 'parqueadero11@example.com', 4.6478, -74.0622, 'BGT', 2, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 12', 30, 17, '1234567890', 'parqueadero12@example.com', 4.6606, -74.0732, 'BGT', 2, '9876543210987', 'TI', 'SEC'),
    ('Parqueadero 13', 33, 5, '1234567890', 'parqueadero13@example.com', 4.6645, -74.0914, 'BGT', 1, '4567890123456', 'CC', 'COV'),
    ('Parqueadero 14', 34, 31, '1234567890', 'parqueadero14@example.com', 4.6780, -74.1179, 'BGT', 1, '9876543210987', 'TI', 'UNC'),
    ('Parqueadero 15', 27, 13, '1234567890', 'parqueadero15@example.com', 4.6301, -74.1551, 'BGT', 1, '4567890123456', 'CC', 'SEC'),
    ('Parqueadero 16', 14, 7, '1234567890', 'parqueadero16@example.com', 4.5756, -74.1112, 'BGT', 2, '9876543210987', 'TI', 'COV'),
    ('Parqueadero 17', 29, 13, '1234567890', 'parqueadero17@example.com', 4.5369, -74.0866, 'BGT', 2, '4567890123456', 'CC', 'UNC'),
    ('Parqueadero 18', 55, 33, '1234567890', 'parqueadero18@example.com', 4.5031, -74.1068, 'BGT', 2, '9876543210987', 'TI', 'SEC'),
    ('Parqueadero 19', 61, 25, '1234567890', 'parqueadero19@example.com', 4.6172, -74.1937, 'BGT', 1, '4567890123456', 'CC', 'COV'),
    ('Parqueadero 19', 25, 13, '1234567890', 'parqueadero19@example.com', 8.7521, -75.8820, 'MTR', 1, '7890123456789', 'CI', 'SEC'),
	('Parqueadero 20', 27, 10, '1234567890', 'parqueadero20@example.com', 8.7533, -75.8838, 'MTR', 1, '7890123456789', 'CI', 'SEC'),
	('Parqueadero 21', 21, 9, '1234567890', 'parqueadero21@example.com', 8.7545, -75.8834, 'MTR', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 22', 40, 21, '1234567890', 'parqueadero22@example.com', 8.76055, -75.86652, 'MTR', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 23', 20, 8, '1234567890', 'parqueadero23@example.com', 8.73601, -75.86277, 'MTR', 3, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 24', 25, 7, '1234567890', 'parqueadero24@example.com', 5.0689, -75.5160, 'MZS', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 25', 18, 12, '1234567890', 'parqueadero25@example.com', 5.0702, -75.4962, 'MZS', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 26', 11, 15, '1234567890', 'parqueadero26@example.com', 5.0673, -75.5010, 'MZS', 3, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 27', 13, 20, '1234567890', 'parqueadero27@example.com', 5.0670, -75.5053, 'MZS', 3, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 28', 16, 25, '1234567890', 'parqueadero28@example.com', 5.0664, -75.5141, 'MZS', 1, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 29', 24, 8, '1234567890', 'parqueadero29@example.com', 4.8147, -75.6970, 'PER', 1, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 30', 16, 11, '1234567890', 'parqueadero30@example.com', 4.8131, -75.6955, 'PER', 1, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 31', 19, 14, '1234567890', 'parqueadero31@example.com', 4.8107, -75.6977, 'PER', 1, '7890123456789', 'CI', 'SEC'),
	('Parqueadero 32', 12, 19, '1234567890', 'parqueadero32@example.com', 4.8083, -75.6792, 'PER', 2, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 33', 15, 24, '1234567890', 'parqueadero33@example.com', 4.8034, -75.6862, 'PER', 2, '9876543210987', 'TI', 'UNC'),
	('Parqueadero 34', 30, 10, '1234567890', 'parqueadero34@example.com', 3.4310, -76.5274, 'CAL', 1, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 35', 26, 13, '1234567890', 'parqueadero35@example.com', 3.4338, -76.5444, 'CAL', 1, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 36', 39, 16, '1234567890', 'parqueadero36@example.com', 3.4349, -76.5445, 'CAL', 2, '7890123456789', 'CI', 'COV'),
	('Parqueadero 37', 22, 19, '1234567890', 'parqueadero37@example.com', 3.4444, -76.5460, 'CAL', 2, '7890123456789', 'CI', 'COV'),
	('Parqueadero 38', 25, 22, '1234567890', 'parqueadero38@example.com', 10.9542, -74.8076, 'BAQ', 3, '9876543210987', 'TI', 'UNC'),
	('Parqueadero 39', 28, 25, '1234567890', 'parqueadero39@example.com', 10.9268, -74.8020, 'BAQ', 1, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 40', 31, 28, '1234567890', 'parqueadero40@example.com', 10.9236, -74.8016, 'BAQ', 3, '9876543210987', 'TI', 'SEC'),
	('Parqueadero 41', 34, 31, '1234567890', 'parqueadero41@example.com', 10.9222, -74.8062, 'BAQ', 1, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 42', 37, 34, '1234567890', 'parqueadero42@example.com', 6.2462, -75.5945, 'MED', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 43', 30, 37, '1234567890', 'parqueadero43@example.com', 6.2586, -75.6090, 'MED', 1, '7890123456789', 'CI', 'UNC'),
	('Parqueadero 44', 43, 40, '1234567890', 'parqueadero44@example.com', 6.2620, -75.6071, 'MED', 2, '9876543210987', 'TI', 'COV'),
	('Parqueadero 45', 66, 43, '1234567890', 'parqueadero45@example.com', 6.2770, -75.5976, 'MED', 1, '7890123456789', 'CI', 'SEC');
    
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
    (3, 1, 'CAL', 'MOT', 1),   
    (4, 1, 'CAL', 'CAR', 1),
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
    (4, 4, 'CTG', 'CAR', 1);

-- Insertar Reservaciones de prueba
INSERT INTO `FOURPARKSDATABASE`.`RESERVATION` (`DATERES`, `STARTTIMERES`, `ENDTIMERES`, `CREATIONDATERES`, `TOTALRES`, `LICENSEPLATE`, `FK_IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `FK_CLIENT_IDUSER`, `FK_CLIENT_IDDOCTYPE`)
VALUES
    ('2024-05-01', '10:00:00', '12:00:00', '2024-05-01', NULL, 'ABC123', 1, 1, 'CAL', 'CAR', '1234567890123', 'CC'),
    ('2024-05-01', '14:00:00', '16:00:00', '2024-05-01', NULL, 'DEF456', 2, 1, 'CAL', 'CAR', '6543210987654', 'TI'),
    ('2024-05-03', '08:00:00', '10:00:00', '2024-05-01', NULL, 'GHI789', 3, 1, 'CAL', 'MOT', '1234567890123', 'CC'),
    ('2024-05-04', '09:00:00', '11:00:00', '2024-05-01', NULL, 'JKL012', 4, 1, 'CAL', 'CAR', '6543210987654', 'TI'),
    ('2024-05-05', '13:00:00', '15:00:00', '2024-05-01', NULL, 'MNO345', 2, 1, 'CAL', 'CAR', '1234567890123', 'CC');


    
