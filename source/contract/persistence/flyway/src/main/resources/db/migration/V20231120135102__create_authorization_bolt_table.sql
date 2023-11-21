create table authorization_bolt
(
    id                serial primary key,
    driver_id         integer not null
        references driver,
    driver_isikukood  numeric not null,
    driver_first_name varchar not null,
    driver_last_name  varchar not null,
    driver_email       varchar not null,
    created           date    not null
);