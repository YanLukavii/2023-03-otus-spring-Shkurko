INSERT INTO Author (id,name) VALUES (1,'Author 1');
ALTER TABLE Author ALTER COLUMN ID RESTART WITH 2;

INSERT INTO Genre (id,name) VALUES (1,'Genre 1');
ALTER TABLE Genre ALTER COLUMN ID RESTART WITH 2;

INSERT INTO Book (name, author_id, genre_id) VALUES ('Book 1', 1, 1);
