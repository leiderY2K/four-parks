-- Eliminar todos los datos de las tablas existentes para evitar duplicados
DELETE FROM `FOURPARKSDATABASE`.`USER`;
DELETE FROM `FOURPARKSDATABASE`.`DOCUMENTTYPE`;
DELETE FROM `FOURPARKSDATABASE`.`PARKING`;
DELETE FROM `FOURPARKSDATABASE`.`CITY`;
DELETE FROM `FOURPARKSDATABASE`.`ADDRESS`;
DELETE FROM `FOURPARKSDATABASE`.`PARKINGTYPE`;
DELETE FROM `FOURPARKSDATABASE`.`SCHEDULE`;

-- Insertar tipos de documento
INSERT INTO `FOURPARKSDATABASE`.`DOCUMENTTYPE` (`IDDOCTYPE`, `DESCDOCTYPE`) 
VALUES 
    ('1', 'DNI'),
    ('2', 'Pasaporte'),
    ('3', 'Carnet de conducir');

-- Insertar usuarios
INSERT INTO `FOURPARKSDATABASE`.`USER` (`IDUSER`, `FIRSTNAME`, `LASTNAME`, `IDDOCTYPEFK`)
VALUES 
    ('1234567890123', 'Juan', 'Pérez', '1'),    -- DNI
    ('9876543210987', 'María', 'Gómez', '2'),    -- Pasaporte
    ('4567890123456', 'Pedro', 'Martínez', '1'), -- DNI
    ('7890123456789', 'Ana', 'López', '3'),      -- Carnet de conducir
    ('6543210987654', 'Luis', 'Rodríguez', '2'); -- Pasaporte

-- Insertar ciudades
ALTER TABLE CITY AUTO_INCREMENT = 1; 
INSERT INTO `FOURPARKSDATABASE`.`CITY` (`NAME`) 
VALUES 
    ('Bogota'),
    ('Medellin'),
    ('Cali');

-- Insertar direcciones en la tabla ADDRESS
INSERT INTO `FOURPARKSDATABASE`.`ADDRESS` (`DESCADDRESS`, `COORDINATESX`, `COORDINATESY`) 
VALUES 
    ('Dirección 1', 4.6544, 74.5678),
    ('Dirección 2', 4.6544, 74.8765),
    ('Dirección 3', 4.6544, 74.7890),
    ('Dirección 4', 4.6544, 74.2222);


-- Insertar tipos de estacionamiento
INSERT INTO `FOURPARKSDATABASE`.`PARKINGTYPE` (`IDPARKINGTYPE`, `DESCPARKINGTYPE`) 
VALUES 
    (1, 'Uncovered'),
    (2, 'SemiCovered'),
    (3, 'Covered');

-- Insertar horarios
INSERT INTO `FOURPARKSDATABASE`.`SCHEDULE` (`IDSCHEDULE`, `STARTTIME`, `ENDTIME`, `SCHEDULETYPE`) 
VALUES 
    (1, '08:00:00', '18:00:00', 'Weekdays'),
    (2, '08:00:00', '20:00:00', 'Weekends'),
    (3, '00:00:00', '23:59:59', 'Full Time');

-- Insertar estacionamientos
INSERT INTO `FOURPARKSDATABASE`.`PARKING` (`NAMEPARK`, `CAPACITY`, `ADDRESS_COORDINATESX`, `ADDRESS_COORDINATESY`, `PARKINGTYPE_IDPARKINGTYPE`, `PHONE`, `EMAIL`, `CITY_IDCITY`, `SCHEDULE_IDSCHEDULE`) 
VALUES 
    ('Parking Lot A', 100, 4.6544, 74.5678, 1, '123-4567', 'parkinglotA@example.com', 1, 1),
    ('Outdoor Park B', 200, 4.6544, 74.8765, 2, '987-6543', 'outdoorparkB@example.com', 2, 2),
    ('Covered Park C', 30, 4.6544, 74.7890, 3, '601-8877', 'parkingexample@example.com', 1, 2),
    ('Outdoor Park A', 56, 4.6544, 74.2222, 2, '312-1233', 'outdorfp@example.com', 1, 1);

