drop table if exists genre;
drop table if exists author;
drop table if exists comment;
drop table if exists book;

create table comment
(
    id   bigint primary key auto_increment,
    text varchar(255),
    book_id bigint
);
create table book
(
    id      bigint primary key auto_increment,
    title   varchar(255),
    genre_id bigint,
    author_id bigint
);

create table genre
(
    id   bigint primary key auto_increment,
    name  varchar(255) unique
);

create table author
(
    id   bigint primary key auto_increment,
    name  varchar(255) unique
);

alter table book
add constraint fk_bookGenre
foreign key (genre_id) references genre(id);

alter table book
add constraint fk_bookAuthor
foreign key (author_id) references author(id);

alter table comment
add constraint fk_commentBook
foreign key (book_id) references book(id) ON DELETE CASCADE;