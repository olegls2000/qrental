create table if not exists revinfo
(
    rev      integer not null
        primary key,
    revtstmp bigint
);
