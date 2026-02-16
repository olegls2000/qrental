alter table car
    add column by_qrent boolean not null default false;
alter table car
    add column by_bolt boolean not null default false;
alter table car
    add column by_forus boolean not null default false;
alter table car
    add column by_uber boolean not null default false;
alter table car
    add column by_tallink boolean not null default false;