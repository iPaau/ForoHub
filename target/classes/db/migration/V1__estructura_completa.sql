-- Tabla Perfil
CREATE TABLE perfiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla Usuario
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de relación Usuario-Perfil (muchos a muchos)
CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (perfil_id) REFERENCES perfiles(id)
);

-- Tabla Curso
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

-- Tabla Topico
CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'NO_RESPONDIDO',
    autor_id BIGINT,
    curso_id BIGINT,
    CONSTRAINT uk_titulo_mensaje UNIQUE (titulo, mensaje(100)),
    CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- Tabla Respuesta
CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    solucion BOOLEAN DEFAULT FALSE,
    topico_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES topicos(id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

-- Datos dummy
INSERT INTO perfiles (nombre) VALUES ('ADMIN'), ('USER');

INSERT INTO usuarios (nombre, correo_electronico, contrasena) VALUES
('Juan Pérez', 'juan@email.com', '123456'),
('Ana López', 'ana@email.com', 'abcdef');

INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES
(1, 1), (2, 2);

INSERT INTO cursos (nombre, categoria) VALUES
('Spring Boot', 'Backend'),
('React', 'Frontend');

INSERT INTO topicos (titulo, mensaje, autor_id, curso_id) VALUES
('Primer tópico', 'Mensaje de prueba', 1, 1),
('Duda sobre React', '¿Cómo usar hooks?', 2, 2);

INSERT INTO respuestas (mensaje, topico_id, autor_id, solucion) VALUES
('Bienvenido al foro', 1, 2, false),
('Puedes usar useState', 2, 1, true);
