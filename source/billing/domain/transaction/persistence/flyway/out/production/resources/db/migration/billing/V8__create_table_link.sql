create table if not exists link
(
    id         serial primary key,
    car_id     integer not null,
    FOREIGN KEY (car_id) REFERENCES car (id),
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    link_type  varchar,
    date_start date    not null,
    date_end   date,
    comment    varchar
);

