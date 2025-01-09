CREATE DATABASE alumnosDB;
USE alumnosDB;

CREATE TABLE alumno (
    numero INT PRIMARY KEY,
    usuario VARCHAR(20) NOT NULL,
    contrasena VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE,
    nota_media FLOAT,
    imagen BLOB
);

CREATE TABLE asignatura (
    codigo INT PRIMARY KEY,
    nombre VARCHAR(20),
    nota FLOAT,
    aluNumero INT,
    FOREIGN KEY (aluNumero) REFERENCES alumno(numero)
);

INSERT INTO alumnosDB.alumno (numero, usuario, contrasena, fecha_nacimiento, nota_media, imagen) VALUES
(1, 'juanperez', '1234', '2000-05-14', 8.5, NULL),
(2, 'mariagomez', 'abcd', '1999-10-22', 9.1, NULL),
(3, 'pedrolopez', 'qwerty', '2001-03-10', 7.8, NULL),
(4, 'laurarojo', '5678', '1998-07-30', 6.4, NULL),
(5, 'carlosecheverria', 'zxcvbn', '2000-12-05', 8.2, NULL);


INSERT INTO alumnosDB.asignatura (codigo, nombre, nota, aluNumero) VALUES
(101, 'Matematicas', 7.5, 1),
(102, 'Fisica', 9.0, 1),
(103, 'Programacion', 8.0, 1),
(104, 'Historia', 7.0, 1),
(105, 'Lengua Espanola', 9.5, 1),

(201, 'Matematicas', 8.2, 2),
(202, 'Fisica', 9.3, 2),
(203, 'Programacion', 9.0, 2),
(204, 'Historia', 8.4, 2),
(205, 'Lengua Espanola', 9.7, 2),

(301, 'Matematicas', 7.0, 3),
(302, 'Fisica', 7.5, 3),
(303, 'Programacion', 8.3, 3),
(304, 'Historia', 6.8, 3),
(305, 'Lengua Espanola', 7.9, 3),

(401, 'Matematicas', 6.5, 4),
(402, 'Fisica', 6.2, 4),
(403, 'Programacion', 6.0, 4),
(404, 'Historia', 6.7, 4),
(405, 'Lengua Espanola', 7.1, 4),

(501, 'Matematicas', 8.0, 5),
(502, 'Fisica', 8.5, 5),
(503, 'Programacion', 8.7, 5),
(504, 'Historia', 7.9, 5),
(505, 'Lengua Espanola', 8.3, 5);

