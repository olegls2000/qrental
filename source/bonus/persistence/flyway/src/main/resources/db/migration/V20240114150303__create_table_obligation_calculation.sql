create table if not exists obligation_calculation
(
    id          serial primary key,
    action_date date    not null,
    comment     varchar,
    q_week_id   integer not null
        constraint q_week_u unique
        constraint q_week_fk references q_week
);