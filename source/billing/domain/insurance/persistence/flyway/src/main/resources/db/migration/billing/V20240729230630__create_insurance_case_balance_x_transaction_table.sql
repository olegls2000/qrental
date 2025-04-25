create table if not exists insurance_case_balance_x_transaction
(
    id                        serial primary key,
    transaction_id            integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    insurance_case_balance_id integer not null,
    FOREIGN KEY (insurance_case_balance_id) REFERENCES insurance_case_balance (id)
);