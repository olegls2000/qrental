create table if not exists weekly_report_transaction
(
    id               serial primary key,
    transaction_id   integer not null,
    FOREIGN KEY (transaction_id) REFERENCES transaction (id),
    weekly_report_id integer not null,
    FOREIGN KEY (weekly_report_id) REFERENCES weekly_report (id)
);