CREATE TABLE IF NOT EXISTS Author (
    id BIGINT auto_increment PRIMARY KEY ,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Genre (
    id BIGINT auto_increment PRIMARY KEY ,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Book (
    id BIGINT auto_increment PRIMARY KEY,
    name VARCHAR(255),
    author_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES Author(id),
    FOREIGN KEY (genre_id) REFERENCES Genre(id)
);
