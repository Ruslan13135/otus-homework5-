drop table if exists genre;
drop table if exists author;
drop table if exists comment;
drop table if exists book;
drop table if exists user;

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

create table user
(
    id   bigint primary key auto_increment,
    username varchar(255) unique,
    password varchar(255)
);

drop table if exists user_role;
create table user_role
(
    user_id bigint,
    roles varchar(255)
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

alter table user_role
add constraint fk_user_role_user
foreign key (user_id) references user(id);