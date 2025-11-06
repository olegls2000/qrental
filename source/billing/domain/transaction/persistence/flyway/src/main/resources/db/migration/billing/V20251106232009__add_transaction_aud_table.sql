create table if not exists transaction_aud
(
    id                     bigint  not null,
    driver_id              bigint ,
    transaction_type_id    bigint ,
    amount                 bigint ,
    date                   date ,
    comment                varchar,
    rev                    integer not null
    constraint fk_transaction_aud
            references revinfo,
    revtype             smallint,
    primary key (rev, id)
);