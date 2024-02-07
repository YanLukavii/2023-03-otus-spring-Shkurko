INSERT INTO authors (id, full_name) VALUES (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3')
ON CONFLICT (id) DO UPDATE SET full_name = EXCLUDED.full_name;

ALTER SEQUENCE authors_id_seq RESTART WITH 4;

INSERT INTO genres (id, name) VALUES (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3')
ON CONFLICT (id) DO NOTHING;

ALTER SEQUENCE genres_id_seq RESTART WITH 4;

INSERT INTO books (id, title, author_id, genre_id) VALUES (1, 'BookTitle_1', 1, 1), (2, 'BookTitle_2', 2, 2), (3, 'BookTitle_3', 3, 3)
ON CONFLICT (id) DO NOTHING;

ALTER SEQUENCE books_id_seq RESTART WITH 4;

INSERT INTO comments (id, text, book_id) VALUES (1, 'Comment_1', 1), (2, 'Comment_2', 2), (3, 'Comment_3', 3)
ON CONFLICT (id) DO NOTHING;

ALTER SEQUENCE comments_id_seq RESTART WITH 4;