DROP TABLE IF EXISTS Author;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Book;

CREATE TABLE Author (
    id BIGINT auto_increment PRIMARY KEY ,
    name VARCHAR(255)
);

CREATE TABLE Genre (
    id BIGINT auto_increment PRIMARY KEY ,
    name VARCHAR(255)
);

CREATE TABLE Book (
    id BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(255),
    author_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES Author(id),
    FOREIGN KEY (genre_id) REFERENCES Genre(id)
);
