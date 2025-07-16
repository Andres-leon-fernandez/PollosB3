drop database pollosb3;

create database PollosB3;



-- TABLA Cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20)     NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    referencia VARCHAR(200)
);
use pollosb3;
select * from cliente;

insert into cliente(dni,nombre,telefono,direccion,referencia) values ('75244813', 'andres leon', '967944806', 'mz z lt 59', 'por ahi');
use PollosB3;
select*from cliente;

-- TABLA Proveedor
CREATE TABLE proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)   NOT NULL,
    ruc VARCHAR(20)       NOT NULL UNIQUE,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    correo VARCHAR(100)
);

-- TABLA Insumo
CREATE TABLE insumo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100)     NOT NULL,
    stock DECIMAL(10,2)     NOT NULL,
    stock_min DECIMAL(10,2) NOT NULL,
    unidad ENUM('KG','LITRO','UNIDAD') NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    proveedor_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
);

-- TABLA Producto
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL,
    precio DECIMAL(10,2)    NOT NULL,
    categoria VARCHAR(50),
    activo BOOLEAN          NOT NULL DEFAULT TRUE
);

-- Productos típicos de una pollería
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Pollo a la Brasa Entero', 60.00, 'Pollos', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('1/2 Pollo a la Brasa', 35.00, 'Pollos', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('1/4 Pollo a la Brasa', 20.00, 'Pollos', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Porción Papas Fritas Grande', 10.00, 'Guarniciones', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Gaseosa Coca-Cola 1.5L', 8.00, 'Bebidas', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Ensalada Clásica', 12.00, 'Ensaladas', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Anticuchos (3 unidades)', 25.00, 'Parrillas', TRUE);
INSERT INTO producto (descripcion, precio, categoria, activo) VALUES ('Crema Huancaína Adicional', 5.00, 'Salsas', TRUE);

use pollosb3;
select * from producto;

-- TABLA Receta (un producto ? muchos insumos)
CREATE TABLE receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);

-- TABLA puente Receta ? Insumo
CREATE TABLE receta_insumo (
    receta_id INT NOT NULL,
    insumo_id INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,  -- ej. 250 (g)
    PRIMARY KEY (receta_id, insumo_id),
    FOREIGN KEY (receta_id) REFERENCES receta(id),
    FOREIGN KEY (insumo_id) REFERENCES insumo(id)
);

-- TABLA Trabajador
CREATE TABLE trabajador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50)    NOT NULL UNIQUE,
    password VARCHAR(64)   NOT NULL,
    dni VARCHAR(20)        NOT NULL UNIQUE,
    nombre VARCHAR(100)    NOT NULL,
    correo VARCHAR(100),
    telefono VARCHAR(20),
    activo BOOLEAN         NOT NULL DEFAULT TRUE,
    tipo ENUM('ADMIN','MOZO','DELIVERY') NOT NULL,
    disponible BOOLEAN     NOT NULL DEFAULT TRUE
);
INSERT INTO trabajador 
  (usuario, password, dni, nombre, correo, telefono, tipo) 
VALUES 
  ('juanmozo', '1234', '87654321', 'Juan López', 'juanmozo@gmail.com', '987654321', 'MOZO'),
('anitamoza', '1234', '23456789', 'Ana Gómez', 'anitamoza@gmail.com', '912345678', 'MOZO'),
('carlosm', '1234', '34567890', 'Carlos Mena', 'carlosm@gmail.com', '998877665', 'MOZO'),
('luciamoza', '1234', '45678901', 'Lucía Torres', 'luciamoza@gmail.com', '911223344', 'MOZO');

select * from trabajador;
-- TABLA Pedido
CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    trabajador_mozo_id INT,
    trabajador_delivery_id INT,
    fecha_hora DATETIME    NOT NULL,
    tipo ENUM('SALON','DELIVERY') NOT NULL,
    total DECIMAL(10,2)    NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (trabajador_mozo_id) REFERENCES trabajador(id),
    FOREIGN KEY (trabajador_delivery_id) REFERENCES trabajador(id)
);
use pollosb3;
INSERT INTO pedido (
    cliente_id,
    trabajador_delivery_id,
    fecha_hora,
    tipo,
    total
) VALUES (
    1,              -- Cliente Juan Pérez
    3,              -- Delivery Pedro
    NOW(),
    'DELIVERY',
    70.50
);
-- TABLA Orden (varias por pedido)
CREATE TABLE orden (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT NOT NULL,
    pedido_id INT NOT NULL,
    cantidad INT        NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES producto(id),
    FOREIGN KEY (pedido_id)   REFERENCES pedido(id)
);



-- TABLA Comprobante
CREATE TABLE comprobante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    fecha_hora DATETIME    NOT NULL,
    tipo_comprobante ENUM('BOLETA','FACTURA') NOT NULL,
    metodo_pago ENUM('YAPE','PLIN','TARJETA','EFECTIVO') NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

-- (Opcional) TABLA Movimientos de almacén
CREATE TABLE almacen_movimiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    insumo_id INT NOT NULL,
    tipo_movimiento ENUM('INGRESO','SALIDA') NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    fecha_hora DATETIME    NOT NULL,
    FOREIGN KEY (insumo_id) REFERENCES insumo(id)
);

-- Productos de ejemplo
INSERT INTO producto (codigo, nombre, precio) VALUES
('P001', 'Pollo Brost', 18.50),
('P002', 'Papas Fritas', 6.00),
('P003', 'Inka Kola', 4.00);

-- Clientes de prueba
INSERT INTO cliente (nombre, dni) VALUES
('Juan Pérez', '74851236'),
('Ana López', '72381233');

-- Pedido manual
INSERT INTO pedido (id_cliente, tipo, fecha_hora, total) VALUES
(1, 'SALON', NOW(), 40.50);

-- Detalles del pedido
INSERT INTO pedido_producto (id_pedido, id_producto, cantidad) VALUES
(1, 1, 2), -- 2 Pollos
(1, 3, 1); -- 1 Inka Kola
