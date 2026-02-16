create table if not exists weekly_report_calculation
(
    id          serial primary key,
    action_date date    not null,
    q_week_id   integer not null,
    comment     varchar,
    FOREIGN KEY (q_week_id) REFERENCES q_week (id)
);