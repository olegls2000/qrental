create table if not exists bonus_calculation_result
(
    id                   serial primary key,
    bonus_program_id     integer not null,
    FOREIGN KEY (bonus_program_id) REFERENCES bonus_program (id),
    bonus_calculation_id integer not null,
    FOREIGN KEY (bonus_calculation_id) REFERENCES bonus_calculation (id),
    transaction_id       integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id)
);