create table if not exists insurance_calculation
(
    id              serial primary key,
    action_date     date    not null,
    comment         varchar,
    start_q_week_id integer not null,
    FOREIGN KEY (start_q_week_id) REFERENCES q_week (id),
    end_q_week_id   integer not null,
    FOREIGN KEY (end_q_week_id) REFERENCES q_week (id)
);