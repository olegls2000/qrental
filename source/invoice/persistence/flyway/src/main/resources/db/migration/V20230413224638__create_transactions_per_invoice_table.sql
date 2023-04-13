create table if not exists transaction_per_invoice
(
    id             serial primary key,
    transaction_id integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    invoice_id     integer not null,
    FOREIGN KEY (invoice_id) REFERENCES invoice (id)
);