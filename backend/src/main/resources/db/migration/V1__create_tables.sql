CREATE TABLE user
(
    id       bigint       not null auto_increment,
    username varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255),
    primary key (id)
);

create table meme
(
    id        bigint not null auto_increment,
    meme_path varchar(255),
    primary key (id)
);

create table reaction
(
    id integer not null,
    primary key (id)
);

CREATE TABLE reaction_givers
(
    meme_id     bigint  not null,
    reaction_id integer not null,
    primary key (meme_id, reaction_id),
    foreign key (meme_id) references meme (id),
    foreign key (reaction_id) references reaction (id)
);

CREATE TABLE reaction_givers_value
(
    meme_id     bigint not null,
    reaction_id integer not null,
    user_id     bigint not null,
    amount      integer,
    primary key (meme_id, reaction_id, user_id),
    foreign key (meme_id, reaction_id) references reaction_givers (meme_id, reaction_id),
    foreign key (user_id) references user (id)
);

CREATE TABLE comment
(
    meme_id         bigint  not null,
    user_id         bigint not null,
    comment_text    TEXT,
    primary key (meme_id, user_id),
    foreign key (meme_id) references meme (id),
    foreign key (user_id) references user (id)
);
