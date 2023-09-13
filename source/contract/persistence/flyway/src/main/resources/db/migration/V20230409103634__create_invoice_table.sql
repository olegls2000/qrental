create table if not exists invoice
(
    id                        serial primary key,
    number                    varchar unique not null,
    week_number               integer        not null,
    driver_id                 integer        not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    driver_call_sign          integer,
    driver_company            varchar        not null,
    driver_company_reg_number varchar        not null,
    driver_company_address    varchar        not null,
    q_firm_id                 integer        not null,
    FOREIGN KEY (q_firm_id) REFERENCES firm (id),
    q_firm_name               varchar        not null,
    q_firm_reg_number         varchar        not null,
    q_firm_vat_number         varchar        not null,
    q_firm_iban               varchar        not null,
    q_firm_bank               varchar        not null,
    created                   date           not null,
    comment                   varchar,
    UNIQUE (week_number, driver_id, q_firm_id)
);
