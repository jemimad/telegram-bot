DROP DATABASE IF EXISTS bot;
CREATE DATABASE bot;
USE bot;

CREATE TABLE localizacao (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL,
	descricao VARCHAR(150) NULL
);

CREATE TABLE categoria (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL,
	descricao VARCHAR(150) NULL
);

CREATE TABLE bem (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(45) NOT NULL,
	descricao VARCHAR(150) NULL,
	id_localizacao INT NOT NULL,
	id_categoria INT NOT NULL,
	FOREIGN KEY (id_localizacao) REFERENCES localizacao(id),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
    ON DELETE RESTRICT
	ON UPDATE CASCADE
);

SELECT * FROM localizacao;
SELECT * FROM categoria;
SELECT * FROM bem;