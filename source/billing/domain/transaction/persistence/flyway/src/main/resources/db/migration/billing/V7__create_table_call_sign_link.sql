create table if not exists call_sign_link
(
    id         serial primary key,
    call_sign  integer not null ,
    driver_id  integer not null ,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    date_start date not null ,
    date_end   date,
    comment    varchar
);

