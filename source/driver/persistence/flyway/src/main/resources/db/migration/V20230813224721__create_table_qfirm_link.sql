create table if not exists firm_link
(
    id         serial primary key,
    firm_id    integer not null,
    FOREIGN KEY (firm_id) REFERENCES firm (id),
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    date_start date    not null,
    date_end   date,
    comment    varchar
);