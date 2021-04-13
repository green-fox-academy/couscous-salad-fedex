CREATE TABLE user
(
    id            bigint       not null auto_increment,
    username      varchar(255) not null,
    email         varchar(255),
    password      varchar(255),
    primary key (id)
);

create table meme (
    id            bigint       not null auto_increment,
    primary key (id)
);

create table reaction
(
    id          integer     not null,
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

CREATE TABLE reaction_givers_list
(
    meme_id     bigint  not null,
    reaction_id integer not null,
    user_id     bigint  not null,
    foreign key (user_id) references user (id),
    foreign key (meme_id, reaction_id) references reaction_givers (meme_id, reaction_id)
);
