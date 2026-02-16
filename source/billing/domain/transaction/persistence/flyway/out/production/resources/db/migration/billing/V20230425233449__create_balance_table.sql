create table if not exists balance
(
    id        serial primary key,
    driver_id integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    amount    integer not null,
    created   date    not null,
    week_number    integer not null
);
