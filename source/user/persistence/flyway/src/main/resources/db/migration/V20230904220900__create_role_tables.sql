create table if not exists role
(
    id   serial primary key,
    name varchar(40) not null unique
);

create table if not exists role_x_user
(
    id      serial primary key,
    user_id integer not null,
    FOREIGN KEY (user_id) REFERENCES user (id),
    role_id integer not null,
    FOREIGN KEY (role_id) REFERENCES role (id)
);

