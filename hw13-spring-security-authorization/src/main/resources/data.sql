merge into authors(id, full_name)
values (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3');
alter table authors alter column id restart with 4;

merge into genres(id, name)
values (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3');
alter table genres alter column id restart with 4;

merge into books(id, title, author_id, genre_id)
values (1, 'BookTitle_1', 1, 1), (2, 'BookTitle_2', 2, 2), (3, 'BookTitle_3', 3, 3);
alter table books alter column id restart with 4;

merge into comments(id, text, book_id)
values (1, 'Comment_1', 1), (2, 'Comment_2', 2), (3, 'Comment_3', 3);
alter table comments alter column id restart with 4;

merge into roles(id, name)
values (1, 'ADMIN'),  (2, 'USER');
alter table roles alter column id restart with 3;

merge into users(id, username, password, role_id)
values (1, 'usr', '$2a$12$p8HOkYJRZREUG.gesNBjl.dB98NULlw43HkfbZFOmzld106ov8Lj6', 2), (2, 'adm', '$2a$12$p8HOkYJRZREUG.gesNBjl.dB98NULlw43HkfbZFOmzld106ov8Lj6', 1);
alter table users alter column id restart with 3;