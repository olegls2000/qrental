create table if not exists transaction_kind
(
    id      serial primary key,
    code    varchar not null,
    name    varchar not null,
    comment varchar
);
