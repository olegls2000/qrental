create table if not exists obligation
(
    id                serial primary key,
    q_week_id         integer not null,
    driver_id         integer not null,
    obligation_amount integer not null,
    positive_amount   integer not null,
    match_count        integer not null,
    comment           varchar,
    UNIQUE (q_week_id, driver_id)
);