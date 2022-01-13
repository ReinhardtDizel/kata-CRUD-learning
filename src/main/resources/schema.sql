create table user
(
    id       bigint       not null AUTO_INCREMENT primary key,
    login    varchar(255) not null,
    name     varchar(255) not null,
    password varchar(255) not null
);

create table role
(
    id   bigint       not null AUTO_INCREMENT primary key,
    name varchar(255) null
);

create table user_role
(
    user_id  bigint not null,
    roles_id bigint not null,
    primary key (user_id, roles_id),
    FOREIGN KEY (user_id) REFERENCES role (id),
    FOREIGN KEY (roles_id) REFERENCES user (id)
);
