create table if not exists driver_potential
(
    id          serial   primary key,
    first_name  varchar  not null,
    last_name   varchar  not null,
    phone       varchar  not null,
    active      boolean  not null,
    comment     varchar
);