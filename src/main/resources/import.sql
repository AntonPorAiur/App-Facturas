INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (1,'Jorge','Lopez','jlopez@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (2,'David','Peña','dpeña@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (3,'Fernando','Becerra','fbecerra@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (4,'Luis','Juarez','ljuarez@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (5,'Ismael','Duran','iduran@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (6,'Rodrigo','Pacheco','rpacheco@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (7,'Javier','Morales','jmorales@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (8,'Alan','Brito','abrito@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (9,'Jorge','Delfin','jdelfin@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (10,'Alan','Brito','abrito@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (11,'Alma','Ledesma','aledesma@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (12,'Xavier','Guerra','xguerra@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (13,'Gonzalo','Zamora','gzamora@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (14,'Oscar','Ramirez','oramirez@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (15,'Lizbeth','Cariño','lcariño@mail.com','2019-01-05');
INSERT INTO clientes (id,nombre,apellido,email,create_at) VALUES (16,'Alan','Brito','balan@mail.com','2019-01-05');

/* Inserts en tabla productos*/
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla', 15600, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Home Theater Sony', 19699, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Laptop Asus G1366', 13499, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Monitor HP 25" HLQ', 1399, NOW());

/* Inserts en tabla facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 3);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Factura Home Theater', 'Equipo adquirido con factura', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 2, 2);




