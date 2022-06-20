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