create table if not exists campaign
(
    id          serial primary key,
    campaign    varchar not null,
    description varchar,
    active      boolean not null DEFAULT FALSE


);