create table if not exists invoice_calculation
(
    id          serial primary key,
    action_date date not null,
    comment     varchar
);

create table if not exists invoice_calculation_result
(
    id                     serial primary key,
    calculation_id integer not null,
    FOREIGN KEY (calculation_id) REFERENCES invoice_calculation (id),
    invoice_id             integer not null,
    FOREIGN KEY (invoice_id) REFERENCES invoice (id)
);

