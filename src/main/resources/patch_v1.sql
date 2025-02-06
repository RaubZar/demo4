DROP TABLE IF EXISTS enterprise;
DROP TABLE IF EXISTS enterprise_type;

CREATE TABLE enterprise_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    comment VARCHAR(1000)
);


CREATE TABLE enterprise (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL,
    comment VARCHAR(1000),
    type_id BIGINT,
    FOREIGN KEY (type_id) REFERENCES enterprise_type(id)
);


INSERT INTO enterprise_type (name, code, comment) VALUES
('Тип 1', 'T1', 'Комментарий для типа 1'),
('Тип 2', 'T2', 'Комментарий для типа 2'),
('Тип 3', 'T3', 'Комментарий для типа 3');


INSERT INTO enterprise (name, code, comment, type_id) VALUES
('Предприятие 1', 'P1', 'Комментарий для предприятия 1', 1),
('Предприятие 2', 'P2', 'Комментарий для предприятия 2', 2),
('Предприятие 3', 'P3', 'Комментарий для предприятия 3', 3);