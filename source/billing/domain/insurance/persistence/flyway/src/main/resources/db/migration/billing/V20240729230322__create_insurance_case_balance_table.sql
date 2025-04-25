create table if not exists insurance_case_balance
(
    id                            serial primary key,
    q_week_id                     integer not null,
    FOREIGN KEY (q_week_id) REFERENCES q_week (id),
    insurance_case_id             integer not null,
    FOREIGN KEY (insurance_case_id) REFERENCES insurance_case (id),
    damage_remaining              integer not null,
    self_responsibility_remaining integer not null,
    description                   varchar,
    active                        boolean not null
);