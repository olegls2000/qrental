create table if not exists driver
(
    id                    serial primary key,
    active                boolean not null,
    first_name            varchar not null,
    last_name             varchar not null,
    isikukood             bigint  not null,
    phone                 varchar not null,
    email                 varchar not null,
    iban1                 varchar not null,
    iban2                 varchar,
    iban3                 varchar,
    driver_license_number varchar,
    driver_license_exp    date,
    taxi_license          varchar,
    address               varchar,
    comment               varchar
);


