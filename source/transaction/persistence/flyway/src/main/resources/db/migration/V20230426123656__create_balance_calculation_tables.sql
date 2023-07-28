create table if not exists balance_calculation
(
    id          serial primary key,
    action_date date not null,
    comment     varchar
);

create table if not exists balance_calculation_result
(
    id             serial primary key,
    calculation_id integer not null,
    FOREIGN KEY (calculation_id) REFERENCES balance_calculation (id),
    balance_id     integer not null,
    FOREIGN KEY (balance_id) REFERENCES balance (id)
);