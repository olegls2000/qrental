create table if not exists obligation_calculation
(
    id          serial primary key,
    action_date date    not null,
    comment     varchar,
    q_week_id   integer not null
        constraint obligation_calculation_u unique
        constraint obligation_calculation_q_week_fk references q_week
);