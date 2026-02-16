create table if not exists q_week
(
    id          serial primary key,
    year        integer not null,
    number      integer not null,
    description varchar not null
);