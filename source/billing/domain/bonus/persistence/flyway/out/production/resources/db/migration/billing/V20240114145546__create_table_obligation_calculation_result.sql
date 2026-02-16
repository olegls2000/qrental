create table if not exists obligation_calculation_result
(
    id             serial primary key,
    obligation_id integer not null,
    FOREIGN KEY (obligation_id) REFERENCES obligation (id),
    obligation_calculation_id integer not null,
    FOREIGN KEY (obligation_calculation_id) REFERENCES obligation_calculation (id)
);