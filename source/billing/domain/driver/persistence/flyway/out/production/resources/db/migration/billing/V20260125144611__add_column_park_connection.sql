alter table billing.driver
    add column if not exists park_connection boolean default false not null;