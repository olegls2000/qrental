create table if not exists billing.branding_verification
(
    id                        serial primary key,
    date                      date,
    driver_id                 integer,
    car_id                    integer,
    car_link_id               integer,
    branding_expiration_date  date,
    foreign key (driver_id) references billing.driver (id),
    foreign key (car_id) references billing.car (id),
    foreign key (car_link_id) references billing.car_link (id)
);


create table if not exists billing.branding_verification_calculation
(
    id          serial primary key,
    action_date date,
    comment     varchar,
    type        varchar
);

create table if not exists billing.branding_verification_calculation_result
(
    id                        serial primary key,
    calculation_id            integer,
    branding_verification_id  integer,
    foreign key (calculation_id) references billing.branding_verification_calculation (id),
    foreign key (branding_verification_id) references billing.branding_verification (id)
);