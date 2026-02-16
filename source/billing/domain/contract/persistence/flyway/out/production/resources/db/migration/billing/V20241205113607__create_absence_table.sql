create table absence
(
    id        serial primary key,
    driver_id integer not null
        references driver,
    q_week_id integer not null
        references q_week,
    comment   varchar,
    CONSTRAINT uc_driver_q_week UNIQUE (driver_id, q_week_id)
);