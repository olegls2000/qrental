create table if not exists deposit
(
    id         serial primary key,
    amount     numeric not null,
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    created_on date    not null,
    comment    varchar
);