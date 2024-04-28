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
INSERT INTO `FOURPARKSDATABASE`.`USER_AUTHENTICATION` (`FK_IDUSER`, `FK_IDDOCTYPE`, `USERNAME`, `PASSWORD`, `ROLE`, `ATTEMPTS`,`BLOCK`)
VALUES
    ('1234567890123', 'CC', 'juanperez', 'contraseña1','CLIENT',0,0),
    ('9876543210987', 'TI', 'mariagomez', 'contraseña2','CLIENT',0,0),
    ('4567890123456', 'CC', 'pedromartinez', 'contraseña3','CLIENT',0,0),
    ('7890123456789', 'CI', 'analopez', 'contraseña4','CLIENT',0,0),
    ('6543210987654', 'TI', 'luisrodriguez', 'contraseña5','CLIENT',0,0);

-- Insertar administradores después de los usuarios y autenticaciones
INSERT INTO `FOURPARKSDATABASE`.`ADMINISTRATOR` (`FK_IDUSER`, `FK_IDDOCTYPE`)
VALUES
    ('9876543210987', 'TI'), -- María Gómez - Tarjeta de identidad
    ('4567890123456', 'CC'); -- Pedro Martínez - Cedula

-- Insertar ciudades
ALTER TABLE CITY AUTO_INCREMENT = 1; 
INSERT INTO `FOURPARKSDATABASE`.`CITY` (`NAME`,`B_TOP`,`B_BOTTOM`,`B_LEFT`,`B_RIGHT`) 
VALUES 
    ('Bogota',-74.7800,-74.8100,4.2900,4.3100),
    ('Medellin',-75.7800,-75.8100,4.2900,4.3100),
    ('Cali',-76.7800,-76.8100,4.2900,4.3100);

-- Insertar direcciones en la tabla ADDRESS
INSERT INTO `FOURPARKSDATABASE`.`ADDRESS` (`DESCADDRESS`, `COORDINATESX`, `COORDINATESY`) 
VALUES 
    ('Dirección 1', 4.6544, -74.5678),
    ('Dirección 2', 4.6544, -74.6765),
    ('Dirección 3', 4.6544, -74.7890),
    ('Dirección 4', 4.6544, -74.2222);


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
INSERT INTO `FOURPARKSDATABASE`.`PARKING` (`NAMEPARK`, `CAPACITY`, `FK_COORDINATESX`, `FK_COORDINATESY`, `PHONE`, `EMAIL`, `FK_IDCITY`, `FK_IDSCHEDULE`, `FK_ADMIN_IDUSER`, `FK_ADMIN_IDDOCTYPE`) 
VALUES 
    ('Parqueadero A', 100, 4.6544, -74.5678, '123-4567', 'parqueaderoA@example.com', 1, 1,'9876543210987', 'TI'),
    ('Parque al aire libre B', 200, 4.6544, -74.6765, '987-6543', 'parqueairelibreB@example.com', 2, 2,'9876543210987', 'TI'),
    ('Parque cubierto C', 30, 4.6544, -74.7890, '601-8877', 'parqueejemplo@example.com', 1, 2,'9876543210987', 'TI'),
    ('Parque al aire libre A', 56, 4.6544, -74.2222, '312-1233', 'parqueaderoA@example.com', 1, 1,'4567890123456', 'CC');
    
-- Insertar Tipos de Vehiculos
INSERT INTO `FOURPARKSDATABASE`.`VEHICLETYPE` (`IDVEHICLETYPE`,`DESCVEHICLETYPE`) 
VALUES
    ('CAR', 'AUTOMOVIL'),
    ('MOT', 'MOTOCICLETA'),
    ('BIC', 'BICICLETA');
    
-- Insertar espacios de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGSPACE` (`IDPARKINGSPACE`, `ISAVAILABLEVE`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `FK_IDPARKINGTYPE`) 
VALUES 
    (1, 1, 1, 1, 'CAR', 'UNC'),   -- Espacio de estacionamiento 1 en Parqueadero A para automóviles descubiertos
    (2, 1, 1, 1, 'CAR', 'COV'),   -- Espacio de estacionamiento 2 en Parqueadero A para automóviles cubiertos
    (3, 1, 2, 2, 'MOT', 'UNC'),   -- Espacio de estacionamiento 3 en Parque al aire libre B para motocicletas descubiertas
    (4, 1, 3, 1, 'CAR', 'SEC');   -- Espacio de estacionamiento 4 en Parque cubierto C para automóviles semi cubiertos

-- Insertar tarifas
INSERT INTO `FOURPARKSDATABASE`.`RATE` (`HOURCOST`, `RESERVATIONCOST`, `FK_IDPARKING`, `FK_IDCITY`, `FK_IDVEHICLETYPE`, `FK_IDPARKINGTYPE`) 
VALUES 
    (5000, 10000, 1, 1, 'CAR', 'UNC'),    -- Tarifa para Parqueadero A, Bogotá, Automóviles descubiertos
    (7000, 12000, 1, 1, 'CAR', 'COV'),    -- Tarifa para Parqueadero A, Bogotá, Automóviles cubiertos
    (3000, 5000, 2, 2, 'MOT', 'UNC'),     -- Tarifa para Parque al aire libre B, Medellín, Motocicletas descubiertas
    (4000, 6000, 3, 1, 'CAR', 'SEC');     -- Tarifa para Parque cubierto C, Bogotá, Automóviles semi cubiertos

    
