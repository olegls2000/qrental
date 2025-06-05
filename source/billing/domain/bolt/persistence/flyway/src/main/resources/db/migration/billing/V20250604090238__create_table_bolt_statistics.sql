CREATE TABLE bolt_statistics
(
    id         SERIAL PRIMARY KEY,
    file_name  varchar not null,
    created_on date    not null,
    region     varchar not null,
    year       int     not null,
    month      int     not null,
    data       bytea,
    UNIQUE (region, year, month)
);