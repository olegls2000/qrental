create table if not exists transaction_type_aud
(
    invoice_included    boolean,
    rev                 integer not null
        constraint fkp372p1bfpem8uepb85j84op5m
            references revinfo,
    revtype             smallint,
    id                  bigint  not null,
    transaction_kind_id bigint,
    comment             varchar(255),
    ui_visible          boolean default true,
    ui_name             varchar,
    code                varchar,
    name_eng            varchar,
    name_rus            varchar,
    name_est            varchar,

    primary key (rev, id)
);
