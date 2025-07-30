alter table billing.driver
    add column if not exists need_report boolean default false not null;