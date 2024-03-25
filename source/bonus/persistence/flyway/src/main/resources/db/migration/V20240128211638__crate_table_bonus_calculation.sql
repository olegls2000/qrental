create table if not exists bonus_calculation
(
    id          serial primary key,
    action_date date    not null,
    comment     varchar,
    q_week_id   integer not null
        constraint bonus_calculation_u unique
        constraint bonus_calculation_q_week_fk references q_week
);