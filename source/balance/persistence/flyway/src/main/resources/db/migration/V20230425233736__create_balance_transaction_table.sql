create table if not exists balance_transaction
(
    id              serial primary key,
    transaction_id  integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    balance_id integer not null,
    FOREIGN KEY (balance_id) REFERENCES balance (id)
);