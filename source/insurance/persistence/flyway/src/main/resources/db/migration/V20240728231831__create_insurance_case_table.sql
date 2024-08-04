create table if not exists insurance_case
(
    id              serial primary key,
    driver_id       integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    car_id          integer not null,
    FOREIGN KEY (car_id) REFERENCES car (id),
    occurrence_date date    not null,
    damage_amount   integer not null,
    description     varchar,
    active          boolean not null
);