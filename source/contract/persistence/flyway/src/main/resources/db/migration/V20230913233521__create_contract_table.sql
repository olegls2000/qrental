create table contract
(
    id                         serial primary key,
    number                     varchar not null unique,
    renter_name                varchar,
    renter_registration_number varchar,
    renter_ceo_name            varchar,
    renter_ceo_isikukood       varchar,
    renter_phone               varchar not null,
    renter_email               varchar not null,
    driver_id                  integer not null
        references driver,
    driver_isikukood           numeric not null,
    driver_licence_number      varchar not null,
    q_firm_id                  integer not null
        references firm,
    q_firm_name                varchar not null,
    q_firm_registration_number varchar not null,
    q_firm_post_address        varchar not null,
    q_firm_email               varchar not null,
    q_firm_ceo                 varchar not null,
    q_firm_ceo_deputy1         varchar,
    q_firm_ceo_deputy2         varchar,
    q_firm_ceo_deputy3         varchar,
    created                    date    not null
);