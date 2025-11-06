create table if not exists transaction_kind_aud
(
    id                     bigint  not null,
    code                   varchar,
    name                   varchar,
    comment                varchar,
    rev                    integer not null
    constraint fk_transaction_kind_aud references revinfo,
    revtype             smallint,
    primary key (rev, id)
);