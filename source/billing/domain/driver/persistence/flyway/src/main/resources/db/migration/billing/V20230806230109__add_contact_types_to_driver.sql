alter table driver
    add column by_telegram boolean not null default false;
alter table driver
    add column by_whatsapp boolean not null default false;
alter table driver
    add column by_viber boolean not null default false;
alter table driver
    add column by_email boolean not null default false;
alter table driver
    add column by_sms boolean not null default false;
alter table driver
    add column by_phone boolean not null default false;