-- жанры
insert into genre (id, name) values (2, 'Фэнтези')
insert into genre (id, name) values (3, 'Фантастика')
insert into genre (id, name) values (4, 'Роман')
-- авторы
insert into author (id, name) values (2, 'Джордж Мартин')
insert into author (id, name) values (3, 'Роджер Желязны')
insert into author (id, name) values (4, 'Лев Толстой')
-- книги
insert into book (id, title, genre_id, author_id) values (2, 'Игра престолов', 2, 2)
insert into book (id, title, genre_id, author_id) values (3, 'Девять принцев Амбера', 3, 3)
insert into book (id, title, genre_id, author_id) values (4, 'Война и мир', 4, 4)