alter table car
    add column custom_rent_active boolean not null default false;

alter table car
    add column custom_rent_amount numeric;
