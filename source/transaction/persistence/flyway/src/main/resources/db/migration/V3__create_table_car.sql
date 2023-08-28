create table if not exists car
(
    id                       serial primary key,
    active                   boolean default true  not null,
    q_rent                   boolean default true  not null,
    reg_number               varchar(15),
    vin                      varchar(17),
    release_date             date,
    manufacturer             varchar(50),
    model                    varchar,
    appropriation            boolean default false not null,
    elegance                 boolean default false not null,
    gear_type                varchar(15),
    fuel_type                varchar(15),
    lpg                      boolean default false not null,
    date_install_lpg         date,
    insurance_firm           varchar(20),
    insurance_date_start     date,
    insurance_date_end       date,
    s_card                   boolean default true  not null,
    key2                     boolean default true  not null,
    gps                      boolean default true  not null,
    technical_inspection_end date,
    gas_inspection_end       date,
    comment                  varchar
);

