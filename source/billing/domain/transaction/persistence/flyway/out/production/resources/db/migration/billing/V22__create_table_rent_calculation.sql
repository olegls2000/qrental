create table if not exists rent_calculation
(
    id          serial primary key,
    action_date date not null,
    comment     varchar
);