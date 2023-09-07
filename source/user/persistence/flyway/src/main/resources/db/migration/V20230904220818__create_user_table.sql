create table if not exists user_account
(
    id         serial primary key,
    first_name varchar(40) not null,
    last_name  varchar(40) not null,
    username   varchar(40) not null unique,
    email      varchar(40) not null unique,
    password   varchar     not null
);