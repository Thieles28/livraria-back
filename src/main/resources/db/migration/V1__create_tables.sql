CREATE TABLE IF NOT EXISTS autor
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT uk_autor_nome UNIQUE (nome)
    );

CREATE TABLE IF NOT EXISTS genero
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT uk_genero_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS livro
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo    VARCHAR(255) NOT NULL,
    autor_id  BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,

    CONSTRAINT fk_livro_autor FOREIGN KEY (autor_id) REFERENCES autor(id),
    CONSTRAINT fk_livro_genero FOREIGN KEY (genero_id) REFERENCES genero(id),
    CONSTRAINT uk_livro_titulo_autor_genero UNIQUE (titulo, autor_id, genero_id)
);

CREATE TABLE IF NOT EXISTS usuario
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL
);