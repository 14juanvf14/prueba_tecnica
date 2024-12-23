CREATE DATABASE IF NOT EXISTS origins_db; -- Creación de la Base
USE origins_db;

CREATE TABLE IF NOT EXISTS origins (
    id INT AUTO_INCREMENT PRIMARY KEY, -- ID autogenerado
    origin VARCHAR(15) NOT NULL -- Número de teléfono (max 15 caracteres, incluyendo códigos de país)
);

INSERT INTO origins (origin) VALUES
('1234567890'),
('0987654321'),
('5551234567'),
('9112233445'),
('3126549870');

SELECT * FROM origins;

