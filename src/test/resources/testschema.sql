drop table if exists book;
drop table if exists author;
drop table if exists genre;

create table book
(
    id      bigint primary key auto_increment,
    title   varchar(255),
    genreId bigint,
    authorId bigint
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
foreign key (genreId) references genre(Id);

alter table book
add constraint fk_bookAuthor
foreign key (authorId) references author(Id);