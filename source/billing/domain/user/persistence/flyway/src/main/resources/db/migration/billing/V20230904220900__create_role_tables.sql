create table if not exists role
(
    id   serial primary key,
    name varchar(40) not null unique,
    comment varchar
);

create table if not exists role_x_user_account
(
    id      serial primary key,
    user_account_id integer not null,
    FOREIGN KEY (user_account_id) REFERENCES user_account (id),
    role_id integer not null,
    FOREIGN KEY (role_id) REFERENCES role (id)
);

