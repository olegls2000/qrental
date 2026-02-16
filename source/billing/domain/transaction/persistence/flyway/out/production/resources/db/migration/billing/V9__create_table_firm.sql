create table if not exists firm
(
    id         serial primary key,
    firm_name  varchar,
    iban       varchar,
    reg_number bigint,
    vat_number varchar,
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    comment    varchar
);

