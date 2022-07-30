-- жанры
insert into genre (id, name) values (1, 'Фэнтези')
insert into genre (id, name) values (2, 'Фантастика')
insert into genre (id, name) values (3, 'Роман')
-- авторы
insert into author (id, name) values (1, 'Джордж Мартин')
insert into author (id, name) values (2, 'Роджер Желязны')
insert into author (id, name) values (3, 'Лев Толстой')
-- книги
insert into book (id, title, genre_id, author_id) values (1, 'Игра престолов', 1, 1)
insert into book (id, title, genre_id, author_id) values (2, 'Девять принцев Амбера', 2, 2)
insert into book (id, title, genre_id, author_id) values (3, 'Война и мир', 3, 3)
-- администратор
insert into user (id, username, password) values (1, 'admin', 'password')
insert into user (id, username, password) values (2, 'user', 'user')
insert into user (id, username, password) values (3, 'banned_user', 'user')
-- роли
insert into user_role (user_id, roles) values (1, 'ADMIN')
insert into user_role (user_id, roles) values (2, 'USER')
insert into user_role (user_id, roles) values (3, 'BANNED_USER')