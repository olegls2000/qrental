create table if not exists transaction_type_aud
(
    id                     bigint  not null,
    code                   varchar,
    name_eng               varchar,
    name_rus               varchar,
    name_est               varchar,
    invoice_included       boolean,
    ui_visible             boolean,
    ui_name                varchar,
    comment                varchar,
    transaction_kind_id    bigint ,
    rev                    integer not null
    constraint fk_transaction_type_aud references revinfo,
    revtype             smallint,
    primary key (rev, id)
);