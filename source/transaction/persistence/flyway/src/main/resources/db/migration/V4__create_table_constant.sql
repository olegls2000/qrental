create table if not exists constant
(
    id          serial primary key,
    constant    varchar,
    value       double precision,
    description varchar
);

