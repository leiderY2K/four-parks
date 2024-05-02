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

-- Insertar administradores después de los usuarios y autenticaciones
INSERT INTO `FOURPARKSDATABASE`.`ADMINISTRATOR` (`IDUSER`, `FK_IDDOCTYPE`)
VALUES
    ('9876543210987', 'TI'), -- María Gómez - Tarjeta de identidad
    ('4567890123456', 'CC'); -- Pedro Martínez - Cedula

-- Insertar clientes después de los usuarios y autenticaciones
INSERT INTO `FOURPARKSDATABASE`.`CLIENT` (`IDUSER`, `FK_IDDOCTYPE`)
VALUES
    ('1234567890123', 'CC'), -- Juan Perez - Cedula
    ('6543210987654', 'TI'); -- Pedro Martínez - Tarjeta de identidad

-- Insertar ciudades
ALTER TABLE CITY AUTO_INCREMENT = 1; 
INSERT INTO `FOURPARKSDATABASE`.`CITY` (`IDCITY`, `NAME`,`B_TOP`,`B_BOTTOM`,`B_LEFT`,`B_RIGHT`) 
VALUES 
    ('BOG', 'Bogota',-74.7800,-74.8100,4.2900,4.3100),
    ('MED', 'Medellin',-75.7800,-75.8100,4.2900,4.3100),
    ('CAL', 'Cali',-76.7800,-76.8100,4.2900,4.3100);

-- Insertar direcciones en la tabla ADDRESS
INSERT INTO `FOURPARKSDATABASE`.`ADDRESS` (`DESCADDRESS`, `COORDINATESX`, `COORDINATESY`) 
VALUES 
    ('Dirección 1', 4.2989, -74.8096),
    ('Dirección 2', 4.3034, -74.8080),
    ('Dirección 3', 4.3030, -74.7996),
    ('Dirección 4', 4.29792, -74.80894);


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
INSERT INTO `FOURPARKSDATABASE`.`PARKING` (`NAMEPARK`, `CAPACITY`, `DISPONIBILITY`, `FK_COORDINATESX`, `FK_COORDINATESY`, `PHONE`, `EMAIL`, `FK_IDCITY`, `FK_IDSCHEDULE`, `FK_ADMIN_IDUSER`, `FK_ADMIN_IDDOCTYPE`,`FK_IDPARKINGTYPE`) 
VALUES 
    ('Parqueadero Cubierto 1', 0, 0, 4.2989, -74.8096, '123-4567', 'parqueaderoA@example.com', 'BOG', 1,'9876543210987', 'TI', 'COV'),
    ('Parque al aire libre 1', 0, 0, 4.3034, -74.8080, '987-6543', 'parqueairelibreB@example.com', 'BOG', 2,'9876543210987', 'TI', 'UNC'),
    ('Parqueadero Cubierto 2', 0, 0, 4.3030, -74.7996, '601-8877', 'parqueejemplo@example.com', 'BOG', 2,'9876543210987', 'TI', 'COV'),
    ('Parqueadero SemiCubierto 1', 0, 0, 4.29792, -74.80894, '312-1233', 'parqueaderoA@example.com', 'BOG', 1,'4567890123456', 'CC', 'SEC');
    
-- Insertar Tipos de Vehiculos
INSERT INTO `FOURPARKSDATABASE`.`VEHICLETYPE` (`IDVEHICLETYPE`,`DESCVEHICLETYPE`) 
VALUES
    ('CAR', 'AUTOMOVIL'),
    ('MOT', 'MOTOCICLETA'),
    ('BIC', 'BICICLETA');


-- Insertar tarifas
INSERT INTO `FOURPARKSDATABASE`.`RATE` (`HOURCOST`, `RESERVATIONCOST`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `ISCOVERED`) 
VALUES 
    (5000, 10000, 1, 'BOG', 'CAR', 1),    
    (4000, 6000, 1, 'BOG', 'MOT', 1),
    (7000, 12000, 2, 'BOG', 'CAR', 0),
    (3000, 5000, 2, 'BOG', 'MOT', 0),
    (3000, 5000, 3, 'BOG', 'CAR', 1),
    (4000, 6000, 3, 'BOG', 'CAR', 0),
    (3000, 5000, 4, 'BOG', 'CAR', 1),
    (4000, 6000, 4, 'BOG', 'CAR', 0);
    
-- Insertar espacios de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGSPACE` (`IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `ISCOVERED`) 
VALUES
    -- Parqueadero 1 
    (1, 1, 'BOG', 'CAR', 1),
    (2, 1, 'BOG', 'CAR', 1),   
    (3, 1, 'BOG', 'MOT', 1),   
    (4, 1, 'BOG', 'CAR', 1),
    -- Parqueadero 2
    (1, 2, 'BOG', 'CAR', 0),
    (2, 2, 'BOG', 'CAR', 0),   
    (3, 2, 'BOG', 'MOT', 0),   
    (4, 2, 'BOG', 'MOT', 0),
    -- Parqueadero 3
    (1, 3, 'BOG', 'CAR', 1),
    (2, 3, 'BOG', 'CAR', 1),   
    (3, 3, 'BOG', 'CAR', 1),   
    (4, 3, 'BOG', 'CAR', 1),
    -- Parqueadero 4
    (1, 4, 'BOG', 'CAR', 1),
    (2, 4, 'BOG', 'CAR', 1),   
    (3, 4, 'BOG', 'CAR', 0),   
    (4, 4, 'BOG', 'CAR', 0);

-- Insertar Reservaciones de prueba
INSERT INTO `FOURPARKSDATABASE`.`RESERVATION` (`DATERES`, `STARTTIMERES`, `ENDTIMERES`, `CREATIONDATERES`, `TOTALRES`, `LICENSEPLATE`, `FK_IDPARKINGSPACE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `FK_CLIENT_IDUSER`, `FK_CLIENT_IDDOCTYPE`)
VALUES
    ('2024-05-01', '10:00:00', '12:00:00', '2024-05-01', NULL, 'ABC123', 1, 1, 'BOG', 'CAR', '1234567890123', 'CC'),
    ('2024-05-02', '14:00:00', '16:00:00', '2024-05-01', NULL, 'DEF456', 2, 1, 'BOG', 'CAR', '6543210987654', 'TI'),
    ('2024-05-03', '08:00:00', '10:00:00', '2024-05-01', NULL, 'GHI789', 3, 2, 'BOG', 'MOT', '1234567890123', 'CC'),
    ('2024-05-04', '09:00:00', '11:00:00', '2024-05-01', NULL, 'JKL012', 4, 3, 'BOG', 'CAR', '6543210987654', 'TI'),
    ('2024-05-05', '13:00:00', '15:00:00', '2024-05-01', NULL, 'MNO345', 2, 4, 'BOG', 'CAR', '1234567890123', 'CC');


    
