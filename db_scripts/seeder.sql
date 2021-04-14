/*
Datos de prueba
*/

DELETE FROM vaca;
DELETE FROM bovino;
DELETE FROM configuracion;
DELETE FROM finca;

INSERT INTO finca (nombre, direccion, area, contacto)
VALUES ('Las Hortensias', 'San Vito', 500, 'Juan Perez');

INSERT INTO CONFIGURACION (id_finca, max_iep, alerta_palpacion, alerta_parto, alerta_destete)
VALUES  (1, 365, 15, 15, 200);

INSERT INTO bovino (id, nombre, fecha_nacimiento, caracteristicas, peso, id_raza, id_finca, es_toro)
VALUES (1, 'Lula', '1999-05-03', 'No hace caso.', 300, 5, 1, 0);
INSERT INTO vaca (id_bovino, id_modo_preñez, id_estado_desarrollo)
VALUES (1, 3, 3);

INSERT INTO bovino (id, nombre, fecha_nacimiento, caracteristicas, peso, id_raza, id_finca, es_toro)
VALUES (2, 'Chirriche', '2005-05-03', 'Agresivo.', 500, 2, 1, 1);

INSERT INTO bovino (id, nombre, fecha_nacimiento, caracteristicas, peso, id_raza, id_padre, id_madre, id_finca, es_toro)
VALUES (3, 'Malacrianza', '2015-10-14', 'Hijo de Chirriche y Lula.', 100, 1, 001, 002, 1, 1);