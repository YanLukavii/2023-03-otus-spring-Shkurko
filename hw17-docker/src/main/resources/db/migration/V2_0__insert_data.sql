
insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text, book_id)
values ('Comment_1', 1), ('Comment_2', 2), ('Comment_3', 3);

insert into roles(name)
values ('ADMIN'),  ('USER');

insert into users(username, password, role_id)
values ('usr', '$2a$12$p8HOkYJRZREUG.gesNBjl.dB98NULlw43HkfbZFOmzld106ov8Lj6', 2), ('adm', '$2a$12$p8HOkYJRZREUG.gesNBjl.dB98NULlw43HkfbZFOmzld106ov8Lj6', 1);