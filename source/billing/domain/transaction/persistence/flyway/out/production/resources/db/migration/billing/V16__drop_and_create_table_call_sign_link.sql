DROP TABLE IF EXISTS call_sign_link;

create table if not exists call_sign_link
(
    id         serial primary key,
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    call_sign_id  integer not null,
    FOREIGN KEY (call_sign_id) REFERENCES call_sign (id),
    date_start date    not null,
    date_end   date,
    comment    varchar
);