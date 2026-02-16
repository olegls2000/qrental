create table if not exists bonus_program
(
    id          serial primary key,
    code        varchar not null,
    name_eng    varchar not null,
    name_rus    varchar not null,
    name_est    varchar not null,
    active      boolean not null,
    description varchar,
    UNIQUE (code)
);