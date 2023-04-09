create table if not exists invoice_item
(
    id         serial primary key,
    invoice_id integer not null,
    FOREIGN KEY (invoice_id) REFERENCES invoice (id),
    amount     integer not null,
    type       varchar not null,
    comment    varchar
);
