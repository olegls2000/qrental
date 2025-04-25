create schema if not exists billing;
create table billing.flyway_schema_history_billing
(
    installed_rank integer                 not null
        constraint flyway_schema_history_billing_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table billing.flyway_schema_history_billing
    owner to postgres;

create index flyway_schema_history_billing_s_idx
    on billing.flyway_schema_history_billing (success);

create table billing.flyway_schema_history_notification
(
    installed_rank integer                 not null
        constraint flyway_schema_history_notification_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table billing.flyway_schema_history_notification
    owner to postgres;

create index flyway_schema_history_notification_s_idx
    on billing.flyway_schema_history_notification (success);

    --drop schema billing cascade;


