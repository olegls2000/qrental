create table if not exists rent_calculation_result
(
    id             serial primary key,
    link_id        integer not null,
    FOREIGN KEY (link_id) REFERENCES link (id),
    transaction_id integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    calculation_id integer not null,
    FOREIGN KEY (calculation_id) REFERENCES rent_calculation (id)
);