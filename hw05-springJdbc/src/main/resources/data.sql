INSERT INTO Author (name) VALUES ('Александр Пушкин');
INSERT INTO Author (name) VALUES ('Федор Достоевский');
INSERT INTO Author (name) VALUES ('Fork');

INSERT INTO Genre (name) VALUES ('Роман');
INSERT INTO Genre (name) VALUES ('Поэма');

INSERT INTO Book (name, author_id, genre_id) VALUES ('Евгений Онегин', 1, 1);
INSERT INTO Book (name, author_id, genre_id) VALUES ('Преступление и наказание', 2, 1);
INSERT INTO Book (name, author_id, genre_id) VALUES ('Пиковая дама', 1, 2);