create table if not exists call_sign
(
    id         serial primary key,
    call_sign  integer not null UNIQUE,
    comment    varchar
);