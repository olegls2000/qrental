create table if not exists transaction_type
(
    id          serial primary key,
    type_tr     varchar,
    description varchar,
    comment     varchar
);

